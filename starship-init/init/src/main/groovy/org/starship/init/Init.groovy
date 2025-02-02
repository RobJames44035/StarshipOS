package org.starship.init

import groovy.util.logging.Slf4j
import org.freedesktop.dbus.exceptions.DBusException
import org.freedesktop.dbus.messages.DBusSignal
import org.starship.osgi.OSGiManager
import org.starship.sdk.dbus.DBusServiceRegistry
import org.starship.sys.PanicException

import java.lang.management.ManagementFactory
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

@Slf4j
class Init {

    static String PRIMARY_CONFIG_PATH = "/etc/starship/config.d/init.cfg"
    static String FALLBACK_CONFIG_PATH = "/default-init.cff"

    // Tables for dynamically managing resources and services
    static ConcurrentHashMap<String, Object> processTable = new ConcurrentHashMap<>()
    static ConcurrentHashMap<String, Object> resourceTable = new ConcurrentHashMap<>()
    static ConcurrentHashMap<String, Object> serviceTable = new ConcurrentHashMap<>()

    // Last heartbeat timestamp from OSGiManager
    static volatile long osgiManagerLastHeartbeat = System.currentTimeMillis()
    static final long HEARTBEAT_TIMEOUT = 10000 // Timeout duration for heartbeats in milliseconds (10 seconds)

    // Instantiate the DBusServiceRegistry (singleton for this process)
    static final DBusServiceRegistry dbusRegistry = new DBusServiceRegistry()

    static void main(final String[] args) {
        try {
            // Check if running as PID 1
            if (ManagementFactory.getRuntimeMXBean().getName().split("@")[0] != "1") {
                log.warn("Warning: This program is not running as PID 1!")
            } else {
                processTable.put("org.starship.init.Init", Init)
            }

            log.info("Initializing StarshipOS...")

            // Initialize the D-Bus registry
            initializeMachineId()
            dbusRegistry.initialize()
            log.info("DBusServiceRegistry initialized.")

            // Register signal handlers (e.g., for receiving heartbeats)
            registerSignalHandlers()

            setupShutdownHook()
            configureSystem()
            log.info("System is up.")
            supervisorLoop()

        } catch (Exception e) {
            log.error("Critical error during system initialization: ${e.message}", e)
            throw new PanicException("PANIC: ${e.message ?: 'Unknown error'}", e)
        }
    }

    static void initializeMachineId() {
        def machineIdPath = Paths.get("/etc/machine-id")
        try {
            if (!Files.exists(machineIdPath)) {
                log.info("machine-id file is missing. Generating...")
                String machineId = UUID.randomUUID().toString().replace("-", "")
                Files.write(machineIdPath, machineId.bytes)
                // Set appropriate permissions
                machineIdPath.toFile().setReadable(true, false)
                machineIdPath.toFile().setWritable(true, true) // Writable by root
                log.info("Generated new machine-id: $machineId")
            } else {
                String existingMachineId = Files.readAllLines(machineIdPath).get(0)
                log.info("machine-id exists: $existingMachineId")
            }
        } catch (Exception e) {
            log.error("Failed to verify or generate machine-id: ${e.message}", e)
            throw new IllegalStateException("Could not initialize machine-id", e)
        }
    }

    /**
     * Configures the system based on the primary or fallback configuration files.
     *
     * @return true if the configuration was successful, false otherwise.
     */
    static boolean configureSystem() {
        try {
            File configFile = new File(PRIMARY_CONFIG_PATH)
            String configContent = null

            if (!configFile.exists()) {
                log.warn("Primary config not found at $PRIMARY_CONFIG_PATH. Attempting to load fallback config: $FALLBACK_CONFIG_PATH")
                def fallbackStream = Init.class.classLoader.getResourceAsStream("default-init.cfg")
                if (fallbackStream == null) {
                    log.error("Fallback configuration not found. Aborting!")
                    throw new PanicException("System configuration missing! Initialization cannot proceed.")
                }
                configContent = fallbackStream.text
                log.info("Fallback configuration loaded from resources.")
            } else {
                log.info("Primary configuration file located at: ${configFile.absolutePath}")
            }

            // Load configuration into the system
            if (!configContent) {
                InitUtil.getInstance().configureSystem(configFile)
            } else {
                InitUtil.getInstance().configureSystem(configContent)
            }

            return true
        } catch (Exception e) {
            log.error("System configuration failed: ${e.message}", e)
            throw new PanicException("System configuration aborted due to critical errors.", e)
        }
    }

    /**
     * Supervises critical services, including OSGiManager.
     * Attempts to recover services and initiates cascading shutdown on unrecoverable failure.
     */
    static void supervisorLoop() {
        log.info("Starting Supervisor Loop...")

        int restartAttempts = 0
        final int maxRestartAttempts = 3

        while (true) {
            try {
                log.info("Running supervision checks for monitored services...")

                // Monitor OSGiManager
                if (isOSGiManagerDown()) {
                    log.warn("OSGiManager detected as down! Attempting to restart...")

                    emitRestartSignal("OSGiManager")
                    restartAttempts++

                    // Allow restart to propagate
                    Thread.sleep(5000)

                    if (!isOSGiManagerDown()) {
                        log.info("OSGiManager successfully restarted.")
                        restartAttempts = 0
                    } else if (restartAttempts >= maxRestartAttempts) {
                        log.error("OSGiManager failed to recover after ${maxRestartAttempts} attempts. Initiating cascading shutdown...")
                        initiateCascadingShutdown()
                        break
                    }
                }

                // Perform other housekeeping (e.g., cleanup zombies)
                reapZombies()
                Thread.sleep(1000) // Polling interval

            } catch (Exception e) {
                log.error("Error in supervisor loop: ${e.message}", e)
            }
        }
    }

    /**
     * Determines if OSGiManager is down based on heartbeat timeout.
     *
     * @return true if no heartbeat has been received within the timeout, false otherwise.
     */
    static boolean isOSGiManagerDown() {
        long currentTime = System.currentTimeMillis()
        boolean isDown = (currentTime - osgiManagerLastHeartbeat) > HEARTBEAT_TIMEOUT
        if (isDown) {
            log.warn("No heartbeat received from OSGiManager within the last ${HEARTBEAT_TIMEOUT} ms. Marking as down.")
        }
        return isDown
    }

    static void emitRestartSignal(String serviceName) {
        dbusRegistry.emitSignal(new ServiceControlSignals.RestartServiceSignal("/" + serviceName, serviceName))
        log.info("RestartServiceSignal emitted for service: ${serviceName}")
    }

    static void initiateCascadingShutdown() {
        log.warn("Initiating cascading shutdown of all services...")
        serviceTable.each { serviceName, _ ->
            dbusRegistry.emitSignal(new ServiceControlSignals.ShutdownServiceSignal("/" + serviceName, serviceName))
            log.info("ShutdownServiceSignal emitted for service: ${serviceName}")
        }
        log.info("Cleanup complete. Exiting system.")
        System.exit(0)
    }

    static void reapZombies() {
        log.info("Reaping zombie processes...")
        processTable.each { name, process ->
            try {
                if (process?.waitFor(0, TimeUnit.SECONDS)) {
                    log.info("Reaped zombie process with name: ${name}")
                    processTable.remove(name)
                }
            } catch (Exception e) {
                log.error("Error reaping process ${name}: ${e.message}", e)
            }
        }
    }

    static void setupShutdownHook() {
        Runtime.runtime.addShutdownHook(new Thread({
            log.info("Shutdown signal received. Cleaning up...")
            processTable.each { name, process ->
                process?.destroy()
                log.info("Terminated process: ${name}")
            }
            dbusRegistry.close()
            log.info("All services shut down gracefully.")
        }))
    }

    /**
     * Registers handlers for incoming signals, including heartbeats.
     */
    static void registerSignalHandlers() {
        dbusRegistry.registerSignalHandler(OSGiManager.OSGiManagerSignals.OSGiHeartbeatSignal) { signal ->
            log.info("Heartbeat signal received from OSGiManager.")
            osgiManagerLastHeartbeat = System.currentTimeMillis()
        }
    }

    /**
     * Control signal definitions for inter-process communication.
     */
    static class ServiceControlSignals {
        static class RestartServiceSignal extends DBusSignal {
            final String serviceName
            RestartServiceSignal(String objectPath, String serviceName) throws DBusException {
                super(objectPath)
                this.serviceName = serviceName
            }
        }
        static class ShutdownServiceSignal extends DBusSignal {
            final String serviceName
            ShutdownServiceSignal(String objectPath, String serviceName) throws DBusException {
                super(objectPath)
                this.serviceName = serviceName
            }
        }
    }
}
