//file:noinspection GroovyInfiniteLoopStatement
package org.starship.osgi

import groovy.util.logging.Slf4j

@Slf4j
class OSGiManager {

    /**
     * Main entry point of the OSGiManager process.
     */
    static void main(final String[] args) {
        while (true) {
            try {
                log.info("OSGiManager running...")
                Thread.sleep(5000) // 5-second polling interval
            } catch (Exception e) {
                log.error("OSGiManager interrupted: ${e.message}", e)
            }
        }
    }
}
