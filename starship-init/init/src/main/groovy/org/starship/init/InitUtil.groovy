package org.starship.init

import groovy.util.logging.Slf4j

@Slf4j
class InitUtil {
    // Static variable to store the DSL content for later use
    static String dslContent = null

    /**
     * Load DSL configuration from primary path or fallback.
     * @param primaryPath Path to primary configuration file
     * @param fallbackPath Path to fallback configuration resource
     * @return true if configuration was successfully loaded, false otherwise
     */
    static boolean loadConfig(String primaryPath, String fallbackPath) {
        try {
            // Check for primary configuration path
            File primaryConfig = new File(primaryPath)
            if (primaryConfig.exists()) {
                log.info("Loading primary configuration from: {}", primaryConfig.absolutePath)
                dslContent = primaryConfig.text
                return true
            }

            // Fallback to the packaged resource
            log.warn("Primary configuration not found at: {}", primaryPath)
            URL fallbackConfig = InitUtil.class.getResource(fallbackPath)
            if (fallbackConfig != null) {
                log.info("Loading fallback configuration from: {}", fallbackConfig.toString())
                dslContent = fallbackConfig.text
                return true
            }

            log.error("Fallback configuration not found at: {}", fallbackPath)
        } catch (Exception e) {
            log.error("Error loading configuration: {}", e.message, e)
        }

        // If we hit this point, loading failed
        dslContent = null
        return false
    }

    /**
     * Evaluate and interpret the DSL configuration and execute its behavior
     * @return true if the DSL executes successfully, false otherwise
     */
    static boolean executeDsl() {
        try {
            // Ensure DSL content is available
            if (dslContent == null) {
                log.error("No DSL content available to execute.")
                return false
            }

            // Use GroovyShell to evaluate the DSL
            def shell = new GroovyShell()
            def dslScript = shell.evaluate(dslContent)

            // Ensure the evaluated DSL has meaningful behavior
            if (dslScript && dslScript instanceof Closure) {
                log.info("Executing DSL script...")
                dslScript.call()
                return true
            } else {
                log.error("Failed to execute DSL: Script did not produce a valid closure or behavior.")
            }
        } catch (Exception e) {
            log.error("Error evaluating DSL: {}", e.message, e)
        }
        return false
    }
}