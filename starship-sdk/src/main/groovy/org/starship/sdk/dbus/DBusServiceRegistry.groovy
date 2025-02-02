package org.starship.sdk.dbus

import groovy.util.logging.Slf4j
import org.freedesktop.dbus.DBusPath
import org.starship.sdk.dbus.services.HighAvailabilityServiceImpl

@Slf4j
class DBusServiceRegistry {

    /**
     * Initialize and register the HighAvailability service on D-Bus.
     */
    void initialize() {
        try {
            def connection = DBusManager.getConnection()

            // Register HighAvailabilityService
            def haService = new HighAvailabilityServiceImpl()
            connection.exportObject(new DBusPath("/HighAvailabilityService"), haService)

            log.info("HighAvailabilityService registered on D-Bus successfully.")
        } catch (Exception e) {
            log.error("Failed to register HighAvailabilityService on D-Bus: ${e.message}", e)
        }
    }
}
