package org.starship.sdk.dbus.services

import groovy.util.logging.Slf4j
import org.freedesktop.dbus.exceptions.DBusExecutionException

@Slf4j
class HighAvailabilityServiceImpl implements HighAvailabilityService {

    // Track monitored services
    private Map<String, Boolean> monitoredServices = [:]

    @Override
    String ping() {
        log.info("Ping received for HighAvailabilityService")
        return "HighAvailabilityService is alive"
    }

    @Override
    void startMonitoring(String serviceName) {
        if (monitoredServices.containsKey(serviceName)) {
            throw new DBusExecutionException("Service '${serviceName}' is already being monitored")
        }

        monitoredServices[serviceName] = true
        log.info("Started monitoring service '${serviceName}' via D-Bus")

        // Example: Start a thread dedicated to monitoring serviceName
        Thread.start {
            while (monitoredServices[serviceName]) {
                log.info("Monitoring service '${serviceName}'...")
                // Your criticalService monitoring logic here

                Thread.sleep(1000) // Health check interval
            }
        }
    }

    @Override
    void stopMonitoring(String serviceName) {
        if (!monitoredServices.containsKey(serviceName)) {
            throw new DBusExecutionException("Service '${serviceName}' was never monitored or is already stopped")
        }

        monitoredServices.remove(serviceName)
        log.info("Stopped monitoring service '${serviceName}' via D-Bus")
    }

/**
 *
 * @return
 */
    @Override
    String getObjectPath() {
        return null
    }
}
