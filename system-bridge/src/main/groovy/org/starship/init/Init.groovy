//file:noinspection unused
package org.starship.init

import com.sun.jna.Library
import com.sun.jna.Native
import org.starship.sys.PanicException
import sun.misc.Signal
import sun.misc.SignalHandler

import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit

class Init {
    // Configuration paths
    static final String PRIMARY_CONFIG_PATH = "/etc/starship/config.d/default.config"
    static final String FALLBACK_CONFIG_PATH = "resources/default-init.config"

    // List of running child processes
    static final List<Process> childProcesses = [].asSynchronized() as List<Process>

    // Load a configuration file dynamically
    static void loadConfig(String configPath) {
        try {
            File configFile = new File(configPath)
            if (!configFile.exists()) {
                println "Configuration file not found: ${configPath}. Attempting fallback configuration..."

                // Attempt to load fallback config
                File fallbackConfigFile = new File(FALLBACK_CONFIG_PATH)
                if (!fallbackConfigFile.exists()) {
                    println "Fallback configuration not found in resources/default-init.config."
                    kernelPanic("Critical configuration files are missing")
                }

                println "Using fallback configuration from ${FALLBACK_CONFIG_PATH}."
                evaluate(fallbackConfigFile)
            } else {
                // Evaluate primary configuration
                println "Loading configuration from: ${configPath}"
                evaluate(configFile)
            }
        } catch (Exception e) {
            // Handle unexpected errors and call kernel panic
            println "Error while reading configuration: ${e.message}"
            e.printStackTrace()
            kernelPanic("Failed to load configuration due to an error: ${e.message}")
        }
    }

    // Include directive for additional configuration files
    static void include(String path) {
        println "Including configuration from: ${path}"
        loadConfig(path) // Recursive call to load the included configuration
    }

    // Kernel panic implementation via JNA
    static void kernelPanic(String message) {
        println "KERNEL PANIC: ${message}"
        // JNA interface to libc for real system shutdown (halt)
        final LibC libc = Native.load("c", LibC)

        // Sync and trigger panic
        libc.sync()
        libc.reboot(0xfee1dead as int) // Magic number for Linux kernel panic
    }

    // Reap zombie processes (PID 1 responsibility)
    static void reapZombies() {
        childProcesses.removeIf { process ->
            try {
                if (process.waitFor(0, TimeUnit.SECONDS)) {
                    println "Reaped zombie process with exit code: ${process.exitValue()}"
                    return true
                }
            } catch (Exception e) {
                println "Error while reaping process: ${e.message}"
            }
            return false
        }
    }

    // Shutdown hook for cleanup during SIGTERM or errors
    static void setupShutdownHook() {
        Runtime.runtime.addShutdownHook(new Thread({
            println "Shutdown signal received. Cleaning up..."
            childProcesses.each { it.destroy() } // Terminate all child processes
            println "All spawned processes terminated. Exiting gracefully."
        }))
    }

    // Spawn a child process and register it
    static void spawnProcess(String command, String name) {
        try {
            println "Spawning process '${name}': ${command}"
            ProcessBuilder builder = new ProcessBuilder(command.split(" "))
            Process process = builder.start()
            childProcesses.add(process)
        } catch (Exception e) {
            println "Failed to spawn process '${name}': ${e.message}"
        }
    }

    // Main method for initialization
    static void main(String[] args) {
        // Check that the process is running as PID 1
        if (ManagementFactory.getRuntimeMXBean().getName().split("@")[0] != "1") {
            println "Warning: This script is not running as PID 1."
        }

        try {
            println "Initializing StarshipOS as PID 1..."

            // Setup shutdown hook
            setupShutdownHook()

            // Load the primary configuration
            loadConfig(PRIMARY_CONFIG_PATH)

            Signal.handle(new Signal("TERM"), new SignalHandler() {
                @Override
                void handle(Signal signal) {
                    System.out.println("Caught SIGTERM, shutting down gracefully...")
                    shutdown()
                }
            })

            Signal.handle(new Signal("INT"), new SignalHandler() {
                @Override
                void handle(Signal signal) {
                    System.out.println("Caught SIGINT, shutting down gracefully...")
                    shutdown()
                }
            })


            // Supervision loop
            //noinspection GroovyInfiniteLoopStatement
            while (true) {
                reapZombies() // Clean up zombie processes
                Thread.sleep(1000) // Reduce CPU usage in the loop
            }
        } catch (Exception e) {
            throw new PanicException("Initialization failed due to: ${e.message}", e)
        }
    }

    static interface LibC extends Library {
        void sync(); // Manages filesystem sync before halting
        int reboot(int howto); // Executes system halt or reboot
    }
}
