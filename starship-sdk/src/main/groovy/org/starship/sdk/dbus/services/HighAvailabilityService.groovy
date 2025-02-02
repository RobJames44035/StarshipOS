package org.starship.sdk.dbus.services

import org.freedesktop.dbus.annotations.DBusInterfaceName
import org.freedesktop.dbus.interfaces.DBusInterface

@DBusInterfaceName("org.starship.sdk.HighAvailabilityService")
interface HighAvailabilityService extends DBusInterface {
    /**
     * Simple ping to check if the service is available.
     * @return String
     */
    String ping()

    /**
     * Start monitoring a service by name.
     * @param serviceName Name of the service
     */
    void startMonitoring(String serviceName)

    /**
     * Stop monitoring a service by name.
     * @param serviceName Name of the service
     */
    void stopMonitoring(String serviceName)
}
