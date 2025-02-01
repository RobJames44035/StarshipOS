//file:noinspection GroovyAssignabilityCheck
package org.starship.init

import com.sun.jna.Native
import groovy.util.logging.Slf4j
import org.starship.jna.CLibWrapper
/**
 * Utility class for system initialization tasks.
 */
@Slf4j
class InitUtil {

    private static InitUtil instance = null // Singleton instance

    // Instance fields
    private File configFile

    // Private constructor (for singleton)
    private InitUtil() {}

    /**
     * Returns the singleton instance of InitUtil.
     * @return the singleton InitUtil instance
     */
    static InitUtil getInstance() {
        if (instance == null) {
            instance = new InitUtil()
        }
        return instance
    }

    /**
     * Loads and configures the system by parsing the DSL and executing tasks.
     *
     * @param configFile the configuration file to parse
     */
    void configureSystem(File configFile) {
        this.configFile = configFile

        try {
            log.info("Loading configuration from: ${configFile.absolutePath}")
            def configScript = new GroovyShell().parse(configFile)

            // Execute the system configuration DSL
            parseConfiguration(configScript)
        } catch (Exception e) {
            log.error("Error occurred while configuring the system with file: ${e.message}", e)
            throw new IllegalStateException("System configuration failed.", e)
        }
    }

    /**
     * Overloaded method to configure the system using an in-memory configuration string.
     *
     * @param cfgStr the configuration content as a String
     */
    void configureSystem(String cfgStr) {
        try {
            log.info("Loading configuration from in-memory string.")
            def configScript = new GroovyShell().parse(cfgStr)

            // Execute the system configuration DSL
            parseConfiguration(configScript)
        } catch (Exception e) {
            log.error("Error occurred while configuring the system with in-memory string: ${e.message}", e)
            throw new IllegalStateException("System configuration failed.", e)
        }
    }

    /**
     * Parses and executes the DSL from the provided configuration script.
     *
     * @param configScript the parsed Groovy script representing the system configuration
     */
    private void parseConfiguration(def configScript) {
        // Define the init root method for the DSL
        configScript.metaClass.init = { Closure closure ->
            closure.delegate = this // Delegate the closure to the current instance
            closure.resolveStrategy = DELEGATE_ONLY
            closure.call() // Execute the closure
        }

        // Execute the configuration script
        configScript.run()
    }

    // ---- DSL Methods ----

    /**
     * Handles the `system` block in the DSL.
     *
     * @param closure the closure defining the system configuration
     */
    def system(Closure closure) {
        closure.delegate = this
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure.call() // Execute system-specific logic
    }

    /**
     * Sets the system hostname.
     *
     * @param hostname the hostname to configure
     */
    static void setHostname(String hostname) {
        log.info("Setting hostname to: $hostname")
        try {
            CLibWrapper.setHostname(hostname ?: "starship-os")
            log.info("Hostname successfully set to: $hostname")
        } catch (Exception e) {
            log.error("Error while setting hostname: ${e.message}")
            throw new IllegalStateException("Unable to set hostname.", e)
        }
    }

    /**
     * Creates a console at /dev/console if it doesn't already exist.
     */
    static void makeConsole() {
        log.info("Checking for /dev/console...")

        File console = new File("/dev/console")
        if (console.exists()) {
            log.info("/dev/console already exists. No action needed.")
            return
        }
        log.info("/dev/console does not exist. Creating it now...")

        // Create the device node using libc's mknod method
        try {
            CLibWrapper.mknod("/dev/console", 0x2000 | 0x600, Native.dev(5, 1))
            log.info("/dev/console created successfully.")
        } catch(Exception e)
            throw new IllegalStateException("Failed to create /dev/console: ${Native.getLastError()}")
        }

    /**
     * Mounts a filesystem based on the provided arguments.
     *
     * @param source the source of the filesystem
     * @param target the mount point
     * @param type the filesystem type
     * @param flags optional mount flags
     * @param data optional mount data
     */
    void mount(String source, String target, String type, long flags = 0L, String data = null) {
        log.info("Mounting filesystem: source=$source, target=$target, type=$type, flags=$flags, data=$data")
        try {
            CLibWrapper.mount(source, target, type, flags, data)
        } catch(Exception e) {
            throw new IllegalStateException("Failed to mount $source to $target: Error code ${Native.getLastError()}")
        }
        Init.resourceTable.put(target, [source, type])
        log.info("Mounted filesystem: $source -> $target")
    }

    /**
     * Spawns a process with the provided command and name.
     *
     * @param spawnArgs a map containing `command` and `name`
     */
    static void spawn(Map spawnArgs) {
        String command = spawnArgs.command
        String name = spawnArgs.name

        if (!command || !name) {
            log.error("Missing required spawn parameters: ${spawnArgs}")
            throw new IllegalArgumentException("Spawn requires 'command' and 'name'!")
        }

        log.info("Spawning process: $name with command: $command")
        Process process = command.execute()
        Init.processTable.put(name, process)

        log.info("Process $name started successfully (PID: ${process.pid()})")
    }

    /**
     * Starts an interactive shell with a welcome message.
     *
     * @param welcomeMessage the message to display on startup
     * @param shellPath the path to the shell
     */
    static void startInteractiveShell(String welcomeMessage, String shellPath) {
        log.info("Starting interactive shell with welcome message: \"$welcomeMessage\" and shell: $shellPath")
        println("\n$welcomeMessage\n")

        try {
            Process shell = new ProcessBuilder(shellPath)
                    .inheritIO()
                    .start()

            int exitCode = shell.waitFor()
            log.info("Interactive shell exited with code: $exitCode")
        } catch (IOException e) {
            log.error("Failed to start interactive shell: ${e.message}", e)
            throw new IllegalStateException("Could not start interactive shell at $shellPath.", e)
        } catch (InterruptedException e) {
            log.error("Interactive shell was interrupted: ${e.message}", e)
            Thread.currentThread().interrupt()
        }
    }
}
