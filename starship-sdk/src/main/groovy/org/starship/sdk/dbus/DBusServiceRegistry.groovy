//file:noinspection GroovyAssignabilityCheck
//file:noinspection unused
package org.starship.sdk.dbus

import groovy.util.logging.Slf4j
import org.freedesktop.dbus.exceptions.DBusException
import org.freedesktop.dbus.messages.DBusSignal
import org.starship.jna.CLibWrapper
import org.starship.sdk.dbus.services.HighAvailabilityServiceImpl

@Slf4j
class DBusServiceRegistry {

    private static def connection

    /**
     * Initialize and register the HighAvailability service on D-Bus.
     * Ensures required directories and files are in place.
     */
    static void initialize() {
        boolean success = true

        log.info("Starting D-Bus initialization...")

        // --- Step 1: Verify and create /var/lib/dbus directory ---
        success &= initializeDBusDirectory("/var/lib/dbus")

        // --- Step 2: Ensure /etc/machine-id exists ---
        success &= ensureMachineId("/etc/machine-id")

        // --- Step 3: Ensure /var/lib/dbus/machine-id exists ---
        success &= ensureMachineId("/var/lib/dbus/machine-id", "/etc/machine-id")

        // --- Step 4: Synchronize file system ---
        if (success) {
            log.info("Synchronizing the file system...")
            try {
                CLibWrapper.sync()
                log.info("File system synchronized successfully.")
            } catch (Exception e) {
                log.error("Error synchronizing the filesystem: ${e.message}", e)
            }
        } else {
            log.warn("Skipping synchronization due to earlier failures.")
        }

        // --- Step 5: Register HighAvailabilityService ---
        if (success) {
            registerHighAvailabilityService()
        } else {
            log.error("Initialization incomplete! Skipping D-Bus registration due to critical failures.")
        }
    }

    /**
     * Ensures the given directory path exists and is correctly configured.
     */
    private static boolean initializeDBusDirectory(String path) {
        try {
            File dbusDir = new File(path)

            // Handle conflicts (file exists but is not a directory)
            if (dbusDir.exists() && !dbusDir.isDirectory()) {
                log.warn("$path exists but is not a directory. Attempting to delete it...")
                if (!dbusDir.delete()) {
                    log.error("Failed to delete file at $path. Ensure it's manually fixed!")
                    return false
                }
                log.info("Deleted conflicting file at $path.")
            }

            // Create directory if it doesn't exist
            if (!dbusDir.exists()) {
                log.info("$path does not exist. Attempting to create it...")
                if (!dbusDir.mkdirs()) {
                    log.error("Failed to create directory at $path. Check permissions!")
                    return false
                }
                log.info("Directory $path created successfully.")
            }

            // Set appropriate permissions (755)
            if (!dbusDir.setReadable(true, false) || !dbusDir.setWritable(true, true) || !dbusDir.setExecutable(true, false)) {
                log.error("Unable to set proper permissions (755) for $path.")
                return false
            }
            log.info("$path permissions set to 755.")
            return true
        } catch (Exception e) {
            log.error("Error initializing directory at $path: ${e.message}", e)
            return false
        }
    }

    /**
     * Ensures the machine ID exists at the given path, and optionally mirrors another source.
     */
    private static boolean ensureMachineId(String targetPath, String sourcePath = null) {
        try {
            File machineIdFile = new File(targetPath)

            if (!machineIdFile.exists()) {
                log.info("$targetPath does not exist. Generating or copying from source...")
                String machineIdContent
                if (sourcePath) {
                    File sourceFile = new File(sourcePath)
                    machineIdContent = sourceFile.text
                } else {
                    machineIdContent = UUID.randomUUID().toString().replace("-", "").toLowerCase()
                    log.info("Generated new machine-id: $machineIdContent")
                }

                machineIdFile.text = machineIdContent
                log.info("Created machine-id at $targetPath.")
            } else if (machineIdFile.isDirectory()) {
                log.error("$targetPath exists but is a directory. This must be manually corrected!")
                return false
            }

            // Set file permissions (644)
            if (!machineIdFile.setReadable(true, false) || !machineIdFile.setWritable(true, true)) {
                log.error("Unable to set proper permissions (644) for $targetPath.")
                return false
            }
            log.info("$targetPath file permissions set to 644.")
            return true
        } catch (IOException e) {
            log.error("Failed to create or validate machine-id at $targetPath: ${e.message}", e)
            return false
        }
    }

    /**
     * Registers the HighAvailabilityService with D-Bus.
     */
    private static void registerHighAvailabilityService() {
        try {
            log.info("Connecting to D-Bus...")
            connection = DBusManager.getConnection()
            if (connection) {
                HighAvailabilityServiceImpl service = new HighAvailabilityServiceImpl()
                connection.exportObject("/HighAvailabilityService", service)
                log.info("Successfully registered HighAvailabilityService on D-Bus.")
            } else {
                log.warn("D-Bus connection is null. Service registration skipped.")
            }
        } catch (Exception e) {
            log.error("Failed to register HighAvailabilityService on D-Bus: ${e.message}", e)
        }
    }

    /**
     * Registers signal handlers for D-Bus communication.
     */
    static void registerSignalHandlers() {
        try {
            log.info("Registering signal handlers for D-Bus...")
            connection?.addSigHandler(ServiceControlSignals.RestartServiceSignal, null) { signal ->
                log.warn("Restart signal received for service: ${signal.serviceName}")
                handleRestartSignal(signal)
            }

            connection?.addSigHandler(ServiceControlSignals.ShutdownServiceSignal, null) { signal ->
                log.warn("Shutdown signal received for service: ${signal.serviceName}")
                handleShutdownSignal(signal)
            }

            log.info("Signal handlers registered successfully.")
        } catch (Exception e) {
            log.error("Failed to register signal handlers: ${e.message}", e)
        }
    }

    private static void handleRestartSignal(ServiceControlSignals.RestartServiceSignal signal) {
        log.info("Handling restart signal for service: ${signal.serviceName}")
    }

    private static void handleShutdownSignal(ServiceControlSignals.ShutdownServiceSignal signal) {
        log.info("Handling shutdown signal for service: ${signal.serviceName}")
    }

    /**
     * Gracefully closes the D-Bus connection.
     */
    static void close() {
        try {
            connection?.disconnect()
            log.info("D-Bus connection closed gracefully.")
        } catch (Exception e) {
            log.error("Error closing D-Bus connection: ${e.message}", e)
        }
    }

    /**
     * Definitions for custom service signals sent over D-Bus.
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
