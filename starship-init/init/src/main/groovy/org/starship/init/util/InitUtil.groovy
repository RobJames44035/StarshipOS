/*
 * StarshipOS Copyright (c) 2025. R. A. James
 */

// InitUtil.groovy
//file:noinspection unused
//file:noinspection GroovyInfiniteLoopStatement
//file:noinspection GroovyAssignabilityCheck
//file:noinspection GroovyFallthrough
package org.starship.init.util

import com.sun.jna.Native
import groovy.util.logging.Slf4j
import org.starship.init.Init
import org.starship.jna.CLibWrapper
import org.starship.service.ServiceRestartPolicy

import static groovy.lang.Closure.DELEGATE_FIRST
/**
 * Utility class for system initialization tasks.
 */
@Slf4j
class InitUtil {

    private static InitUtil instance = null // Singleton instance

    // Instance fields for properties
    private String logLevel
    private String logLocation

    private InitUtil() {} // Private constructor for singleton

    /**
     * Returns the singleton instance of InitUtil.
     *
     * @return the singleton InitUtil instance
     */
    static InitUtil getInstance() {
        if (instance == null) {
            instance = new InitUtil()
        }
        return instance
    }

    /**
     * Handles the `services` block in the DSL.
     *
     * Parses and defines all services using the provided closure.
     *
     * @param closure The closure defining all services.
     */
    void services(Closure closure) {
        log.info("Beginning service configuration...")
        closure.delegate = this // Bind closure to InitUtil instance
        closure.resolveStrategy = DELEGATE_FIRST
        closure.call() // Execute the closure block
        log.info("Service configuration completed. ${Init.resources.serviceTable.size()} service(s) defined.")
    }

    /**
     * Configures a single service within the `services` block.
     *
     * Adds the service definition to the `serviceTable` from `Init.groovy`.
     *
     * @param serviceArgs A map containing service properties (e.g., name, command, policy).
     */
    @SuppressWarnings('GroovyAssignabilityCheck')
    static void service(Map serviceArgs) {
        if (!serviceArgs.name || !serviceArgs.command) {
            log.error("Service definition failed: 'name' or 'command' is missing.")
            return
        }

        // Default values for optional fields
        serviceArgs.descr = serviceArgs.descr ?: "No description available"
        serviceArgs.policy = serviceArgs.policy ?: ServiceRestartPolicy.NO
        serviceArgs.restartDelay = serviceArgs.restartDelay ?: 0

        // Validate 'policy' as an enumeration
        if (!(serviceArgs.policy instanceof ServiceRestartPolicy)) {
            log.error("Invalid restart policy for service '${serviceArgs.name}'. Falling back to NO policy.")
            serviceArgs.policy = ServiceRestartPolicy.NO
        }

        // Handle optional lifecycle events (defaults to no-op)
        serviceArgs.beforeStart = serviceArgs.beforeStart ?: { -> /* No action */ }
        serviceArgs.afterStart = serviceArgs.afterStart ?: { -> /* No action */ }
        serviceArgs.onFailure = serviceArgs.onFailure ?: { -> /* No action */ }

        // Validate hooks as Closures
        validateLifecycleHook(serviceArgs, "beforeStart")
        validateLifecycleHook(serviceArgs, "afterStart")
        validateLifecycleHook(serviceArgs, "onFailure")

        log.info("Defining service: ${serviceArgs.name}")
        log.debug("Command: ${serviceArgs.command}")
        log.debug("Description: ${serviceArgs.descr}")
        log.debug("Restart Policy: ${serviceArgs.policy}")
        log.debug("Restart Delay: ${serviceArgs.restartDelay} ms")

        // Add service definition to the serviceTable
        if (Init.resources.serviceTable.containsKey(serviceArgs.name)) {
            log.warn("Service '${serviceArgs.name}' is already defined. Overwriting definition.")
        }
        Init.resources.serviceTable.put(serviceArgs.name, serviceArgs)
    }

    private static void validateLifecycleHook(Map serviceArgs, String hookName) {
        if (!(serviceArgs[hookName] instanceof Closure)) {
            log.warn("Invalid lifecycle hook '${hookName}' for service '${serviceArgs.name}'. Ignoring.")
            serviceArgs[hookName] = { -> /* No action */ } // Default to a no-op
        }
    }

    /**
     * Starts all services defined in the `serviceTable`.
     *
     * Spawns each service process and tracks them in `processTable`.
     */
    static void startServices() {
        log.info("Starting ${Init.resources.serviceTable.size()} service(s)...")

        Init.resources.serviceTable.each { String serviceName, Map service ->
            try {
                log.info("Starting service: $serviceName")

                // Spawn the process for this service TODO arguments
                Process process = new ProcessBuilder(service.command.split(" ")).inheritIO().start()
                Init.resources.processTable.put(serviceName, process)
                log.info("Service '$serviceName' started successfully.")
                // Handle restart policy
                switch(service.policy) {
                    case ServiceRestartPolicy.NO:
                        break
                    case ServiceRestartPolicy.ON_SUCCESS:
                        break
                    case ServiceRestartPolicy.ON_ABNORMAL:
                        break
                    case ServiceRestartPolicy.ON_ABORT:
                        break
                    case ServiceRestartPolicy.ON_FAILURE:
                        break
                    case ServiceRestartPolicy.ON_WATCHDOG:
                        break
                    case ServiceRestartPolicy.ALWAYS:
                        handleServiceRestart(service as Map)
                        break
                }

            } catch (Exception e) {
                log.error("Failed to start service '$serviceName': ${e.message}", e)
            }
        }
    }

    /**
     * Handles automatic restart for services with a "restart-always" policy.
     *
     * @param service The service definition map.
     */
    private static void handleServiceRestart(Map service) {
        Thread.start {
            while (true) {
                try {
                    // Wait for the process to finish
                    Process process = Init.resources.processTable.get(service.name) as Process
                    process.waitFor()
                    log.warn("Service '${service.name}' has terminated unexpectedly. Restarting...")

                    // Delay before restarting
                    Thread.sleep(service.restartDelay * 1000)

                    // Restart the service TODO arguments
                    Process restartedProcess = new ProcessBuilder(service.command.split(" ")).inheritIO().start().waitFor(0)
                    Init.resources.processTable.put(service.name, restartedProcess) // Replace old process reference
                    log.info("Service '${service.name}' restarted successfully.")
                } catch (Exception e) {
                    log.error("Error while restarting service '${service.name}': ${e.message}", e)
                }
            }
        }
    }

    /**
     * Handles the `system` block in the DSL.
     *
     * @param closure the closure defining system configuration
     */
    void system(Closure closure) {
        log.info("Configuring system block...")
        closure.delegate = this  // Delegate the closure to this instance of InitUtil
        closure.resolveStrategy = DELEGATE_FIRST
        closure.call()            // Execute the closure logic
    }

    // ---- Properties ----

    void setLevel(String level) {
        this.logLevel = level
        log.info("Log level set to $level")
    }

    String getLevel() {
        return this.logLevel
    }

    void setLocation(String location) {
        this.logLocation = location
        log.info("Log location set to $location")
    }

    String getLocation() {
        return this.logLocation
    }

    // ---- DSL Parsing ----

    /**
     * Loads and configures the system using a Groovy DSL script.
     *
     * @param configScript the parsed Groovy configuration script
     */
    private void parseConfiguration(def configScript) {
        configScript.metaClass.init = { Closure closure ->
            closure.delegate = this
            closure.resolveStrategy = DELEGATE_FIRST
            closure.call()
        }

        // Expose specific InitUtil static methods as callable in DSL
        configScript.metaClass.makeConsole = { -> makeConsole() }
        configScript.metaClass.setHostname = { String hostname -> setHostname(hostname) }
        configScript.metaClass.mount = { String a, String b, String c, long d, String e ->
            mount(a, b, c, d, e)
        }
        configScript.metaClass.spawn = { Map args -> spawn(args) }

        // Execute the DSL configuration
        configScript.run()
    }

    /**
     * Configures the system using a configuration file.
     *
     * @param configFile the configuration file to parse
     */
    void configureSystem(File configFile) {
        try {
            log.info("Loading configuration from: ${configFile.absolutePath}")
            def configScript = new GroovyShell().parse(configFile)
            parseConfiguration(configScript)
        } catch (Exception e) {
            log.error("Error occurred while configuring the system: ${e.message}", e)
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
            parseConfiguration(configScript)
        } catch (Exception e) {
            log.error("Error occurred while configuring the system: ${e.message}", e)
        }
    }

    // ---- Static DSL Methods ----

    static void setHostname(String hostname) {
        if (!hostname) {
            throw new IllegalArgumentException("Hostname must not be null or empty")
        }
        log.info("Setting hostname to: $hostname")
        // Call JNA or system-specific method for hostname setting
    }


    /**
    * Creates the /dev/console device node if it does not exist.
    *
    * This method checks for the existence of the `/dev/console` device. If the device
    * does not exist, it attempts to create it programmatically using system-specific
    * methods. The device node is created with the appropriate mode and major/minor numbers
    * based on Unix/Linux standards.
    *
    * @throws IllegalStateException if the node creation fails or encounters an error.
    */
    static void makeConsole() {
        log.info("Checking and creating /dev/console if necessary...")

        File consoleDevice = new File("/dev/console")
        if (consoleDevice.exists()) {
            log.info("/dev/console already exists. No action needed.")
            return
        }

        try {
            // Mode: Character device with 0600 permissions
            int mode = 0x2000 /* S_IFCHR */ | 0x180 // 0x180 is hexadecimal for octal 0600
            int dev = (5 << 8) | 1 // Major 5, Minor 1

            // Call the mknod system function via our CLibWrapper
            int result = CLibWrapper.mknod("/dev/console", mode, dev) as int
            if (result == 0) {
                log.info("/dev/console created successfully.")
            } else {
                log.error("Failed to create /dev/console. Error code: ${Native.getLastError()}")
                throw new IllegalStateException("Error creating /dev/console. Check permissions!")
            }
        } catch (Exception e) {
            log.error("Error occurred while creating /dev/console: ${e.message}", e)
            throw new IllegalStateException("Failed to create /dev/console", e)
        }
    }


    /**
    * Mounts a file system if the target is not already mounted.
    *
    * This method uses the underlying system calls to mount the given source to the specified target.
    * If the `target` is already mounted, it skips the mounting process.
    *
    * @param source the source of the file system to be mounted
    * @param target the directory where the file system should be mounted
    * @param type the type of the file system (e.g., ext4, nfs, etc.)
    * @param flags (optional) the mount flags, default is 0L
    * @param data (optional) additional data for mounting, default is null
    * @throws IllegalStateException if an error occurs during the mounting process
    */
    static void mount(String source, String target, String type, long flags = 0L, String data = null) {
        log.info("Checking if $target is already mounted...")

        // Check if the target is already mounted
        if (isMounted(target)) {
            log.info("$target is already mounted. Skipping mounting.")
            return
        }

        log.info("Mounting filesystem: source=$source, target=$target, type=$type, flags=$flags, data=$data")
        try {
            // Call to the actual mounting logic (backed by system calls or wrappers)
            CLibWrapper.mount(source, target, type, flags, data)
            Init.resources.resourceTable.put(target, [source, type]) // Track the mounted resource
            log.info("Successfully mounted $source to $target")
        } catch (Exception e) {
            log.error("Failed to mount filesystem: ${e.message}", e)
        }
    }

    /**
     * Checks if the given target is already mounted.
     *
     * @param target the mount point to check
     * @return true if the target is mounted, false otherwise
     */
    static boolean isMounted(String target) {
        try {
            // Read the /proc/mounts file to check current mounts
            File procMounts = new File("/proc/mounts")
            if (!procMounts.exists()) {
                return false // Fail-safe: assume not mounted
            }

            // Iterate through each line of /proc/mounts and check for the target
            for (String line : procMounts.readLines()) {
                String[] parts = line.split("\\s+") // Split the line into components
                if (parts.size() >= 2 && parts[1] == target) {
                    log.info("$target is already mounted.")
                    return true
                }
            }
        } catch (Exception e) {
            log.error("Error while checking mount status for $target: ${e.message}", e)
        }

        return false // Default to not mounted
    }

    /**
    * Spawns a new process based on the provided parameters.
    *
    * This method allows you to spawn a new process given a command and a name.
    * It validates the required parameters and logs the process creation.
    *
    * @param spawnArgs a map containing the required spawn arguments.
    *                  Must include the following keys:
    *                  - `command`: The command to be executed (String).
    *                  - `name`: The name to identify the spawned process (String).
    */
    static void spawn(Map spawnArgs) {
        String command = spawnArgs.command
        String name = spawnArgs.name

        if (!command || !name) {
            log.error("Missing required spawn parameters: ${spawnArgs}")
        }

        log.info("Spawning process: $name with command: $command")

        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "))
        processBuilder.inheritIO()
        Process spawnProcess = processBuilder.start()
        spawnProcess.waitFor()

        Init.resources.processTable.put(spawnArgs.name, spawnProcess)
        log.info("Process $name started successfully.")
    }

    /**
     * Interactive shell adapted for logon.
     */
    static void interactiveShell(String welcomeMessage, String shellPath) {
        try {
            File shellFile = new File(shellPath)
            if (!shellFile.exists() || !shellFile.canExecute()) {
                log.error("Path to shell is invalid: $shellPath")
            }
            // Start a shell
            println(welcomeMessage)
            ProcessBuilder processBuilder = new ProcessBuilder(shellPath).inheritIO()
            Process shellProcess = processBuilder.start()
            shellProcess.waitFor()
            Init.resources.processTable.put(shellPath, shellProcess)
        } catch (Exception e) {
            log.error("Failed to launch shell: ${e.message}")
        }
    }
}
