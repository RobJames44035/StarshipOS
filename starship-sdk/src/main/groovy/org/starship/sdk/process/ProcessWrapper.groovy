package org.starship.sdk.process

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

/**
 * Wrapper class for managing external processes. Provides functionalities 
 * to spawn and terminate processes using specified commands, along with 
 * handling dependencies.
 *
 * This class uses Groovy's @Slf4j for logging and @CompileStatic for 
 * compile-time type checking to ensure performance benefits.
 *
 * @author R.A. James
 */
@Slf4j
@CompileStatic
class ProcessWrapper implements Serializable {

    static final long serialVersionUID = 66L

    private String name                      // Name of the process
    private String command                   // Command to invoke
    private Map<String, Object> dependencies // Dependencies injected, FQN -> Object
    private Process process                  // Underlying process reference

    /**
    * Constructs a new ProcessWrapper instance.
    *
    * @param name The name of the process. This is used for logging and identification purposes.
    * @param command The command to execute the process, expressed as a string.
    * @param dependencies (Optional) A map of dependencies where the key is the fully qualified
    *                     name (FQN) and the value is the corresponding object. Defaulted to an empty map.
    */
    ProcessWrapper(String name, String command, Map<String, Object> dependencies = [:]) {
        this.name = name
        this.command = command
        this.dependencies = dependencies
    }

    /**
    * Starts the process using the previously defined command.
    *
    * This method logs the initiation of the process, spawns it, and logs the PID if successful. 
    * If an error occurs, it logs the error and rethrows the exception.
    *
    * @throws Exception If the process fails to start.
    */
    Map<String,Object> start() {
        try {
            log.info("Spawning process '${name}': ${command}")
            ProcessBuilder builder = new ProcessBuilder(command.split(" "))
            process = builder.start()
            log.info("Process '${name}' spawned with PID: ${process.pid()}.")
            return ["process": process, "eventBus": dependencies.get("org.starship.eventcore.SystemEventBus")]
        } catch (Exception e) {
            log.error("Failed to spawn process '${name}': ${e.message}", e)
            throw e
        }
    }

    /**
    * Stops the process managed by this wrapper.
    *
    * This method attempts to terminate the underlying process referenced by the `process` field.
    * If the process is running, it is terminated, and a log message is generated indicating the
    * successful termination. If the process is not running, a warning is logged instead.
    *
    * Note: This method makes use of `Process.destroy()` to terminate the process.
    *
    * @see Process#destroy()
    */
    void stop() {
        if (process && process.isAlive()) {
            process.destroy()
            log.info("Process '${name}' with PID ${process.pid()} has been terminated.")
        } else {
            log.warn("Attempted to stop process '${name}' but it is not running.")
        }
    }

    /**
    * Retrieves a dependency by its fully qualified name.
    *
    * @param fullyQualifiedName The fully qualified name (FQN) of the dependency to retrieve.
    * @return The corresponding dependency object if it exists, or null if not found.
    */
    Object getDependency(String fullyQualifiedName) {
        return dependencies.get(fullyQualifiedName)
    }
}
