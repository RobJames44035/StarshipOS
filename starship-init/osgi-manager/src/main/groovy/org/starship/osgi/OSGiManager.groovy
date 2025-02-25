/*
 *
 *  *
 *  * StarshipOS $file.filename Copyright (c) 2025 R. A. James
 *  * UPDATED: 2/25/25, 1:18 PM by rajames
 *  *
 *  * StarshipOS is licensed under GPL2, GPL3, Apache 2
 *  *
 *
 *
 */

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
