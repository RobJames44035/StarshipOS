package org.starship.sdk.dbus

import org.freedesktop.dbus.connections.impl.DBusConnection
import org.freedesktop.dbus.exceptions.DBusException
import groovy.util.logging.Slf4j

/**
 * Singleton for managing the D-Bus connection.
 */
@Slf4j
class DBusManager {
    private static DBusConnection connection

    /**
     * Establishes a connection to the D-Bus System Bus.
     * @return DBusConnection
     * @throws DBusException if connection fails
     */
    static synchronized DBusConnection getConnection() throws DBusException {
        if (!connection || !connection.isConnected()) {
            log.info("Establishing a connection to the D-Bus System Bus...")
            connection = DBusConnection.getConnection(DBusConnection.DBusBusType.SYSTEM)
        }
        return connection
    }

    /**
     * Disconnects the D-Bus connection cleanly.
     */
    static synchronized void disconnect() {
        try {
            if (connection && connection.isConnected()) {
                log.info("Disconnecting from D-Bus...")
                connection.close()
            }
        } catch (Exception e) {
            log.error("Error while disconnecting from D-Bus: ${e.message}", e)
        }
    }
}
