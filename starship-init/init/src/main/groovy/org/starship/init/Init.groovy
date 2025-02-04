//file:noinspection GroovyInfiniteLoopStatement
package org.starship.init

import groovy.util.logging.Slf4j
import org.starship.sys.PanicException

import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit

@Slf4j
class Init {

    static String PRIMARY_CONFIG_PATH = "/etc/starship/config.d/init.cfg"
    static String FALLBACK_CONFIG_PATH = "/default-init.cfg"

    // Tables for dynamically managing resources and services
    static final SystemResources resources = SystemResources.getInstance()

    // Last heartbeat timestamp from OSGiManager
//    static volatile long osgiManagerLastHeartbeat = System.currentTimeMillis()
//    static final long HEARTBEAT_TIMEOUT = 10000 // Timeout duration for heartbeats in milliseconds (10 seconds)

    // Instantiate the DBusServiceRegistry (singleton for this process)
//    static final DBusServiceRegistry dbusRegistry = new DBusServiceRegistry()

    static void main(final String[] args) {
        try {
            if (ManagementFactory.getRuntimeMXBean().getName().split("@")[0] != "1") {
                log.warn("Warning: This program is not running as PID 1!")
            } else {
                resources.processTable.put("org.starship.init.Init", this as Process)
            }

            log.info("Initializing StarshipOS...")
            setupShutdownHook()
            configureSystem()
            log.info("System is up.")
            supervisorLoop()

        } catch (Exception e) {
            log.error("Critical error during system initialization: ${e.message}", e)
            throw new PanicException("PANIC: ${e.message ?: 'Unknown error'}", e)
        }
    }

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

    static void supervisorLoop() {
        log.info("Starting Supervisor Loop...")
        while (true) {
            try {
                log.info("Running supervision checks...")
                reapZombies()
                Thread.sleep(1000) // Supervisor polling interval
            } catch (Exception e) {
                log.error("Error in supervisor loop: ${e.message}", e)
            }
        }
    }

//    static boolean isOSGiManagerDown() {
//        long currentTime = System.currentTimeMillis()
//        boolean isDown = (currentTime - osgiManagerLastHeartbeat) > HEARTBEAT_TIMEOUT
//        if (isDown) {
//            log.warn("No heartbeat received from OSGiManager within ${HEARTBEAT_TIMEOUT} ms.")
//        }
//        return isDown
//    }

//    static void emitRestartSignal(String serviceName) {
//        dbusRegistry.emitSignal(new ServiceControlSignals.RestartServiceSignal("/" + serviceName, serviceName))
//        log.info("RestartServiceSignal emitted for service: ${serviceName}")
//    }

//    static void initiateCascadingShutdown() {
//        log.warn("Shutting down all services...")
//        resources.serviceTable.each { serviceName, _ ->
//            dbusRegistry.emitSignal(new ServiceControlSignals.ShutdownServiceSignal("/" + serviceName, serviceName))
//            log.info("ShutdownServiceSignal emitted for service: ${serviceName}")
//        }
//        System.exit(0)
//    }

    static void reapZombies() {
        log.info("Reaping zombie processes...")
        resources.processTable.each { String name, Process process ->
            try {
                if (process?.waitFor(0, TimeUnit.SECONDS)) {
                    log.info("Reaped zombie process: ${name}")
                    resources.processTable.remove(name)
                }
            } catch (Exception e) {
                log.error("Error reaping process ${name}: ${e.message}", e)
            }
        }
    }

    static void setupShutdownHook() {
        Runtime.runtime.addShutdownHook(new Thread({
            log.info("Shutdown hook triggered. Cleaning up...")
            resources.processTable.each { String name, Object process ->
                process?.destroy()
                log.info("Terminated process: ${name}")
            }
            dbusRegistry.close()
        }))
    }

//    static void registerSignalHandlers() {
//        dbusRegistry.registerSignalHandler(OSGiManager.OSGiManagerSignals.OSGiHeartbeatSignal) { signal ->
//            log.info("Heartbeat received from OSGiManager.")
//            osgiManagerLastHeartbeat = System.currentTimeMillis()
//        }
//    }

//    static class ServiceControlSignals {
//        static class RestartServiceSignal extends DBusSignal {
//            final String serviceName
//
//            RestartServiceSignal(String objectPath, String serviceName) throws DBusException {
//                super(objectPath)
//                this.serviceName = serviceName
//            }
//        }
//
//        static class ShutdownServiceSignal extends DBusSignal {
//            final String serviceName
//
//            ShutdownServiceSignal(String objectPath, String serviceName) throws DBusException {
//                super(objectPath)
//                this.serviceName = serviceName
//            }
//        }
//    }
}
