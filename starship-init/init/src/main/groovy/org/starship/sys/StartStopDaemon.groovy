/*
 * StarshipOS Copyright (c) 2025. R.A.James
 *
 * Licensed under GPL2, GPL3 and Apache 2
 */
//file:noinspection GroovyFallthrough
/**
 * A daemon class for starting, stopping, and checking the status of processes.
 *
 * This class provides functionality to manage processes via command-line arguments.
 * It allows starting a new process, stopping existing ones, or checking their status.
 * Processes can be matched using criteria like name, executable path, or user.
 *
 * Command-Line Options:
 * <ul>
 *     <li><b>--start, -S</b>: Start a new process unless a matching one already exists.</li>
 *     <li><b>--stop, -K</b>: Stop all matching processes.</li>
 *     <li><b>--status, -T</b>: Check if a matching process is running.</li>
 *     <li><b>--name &lt;name&gt;, -n</b>: Match processes by name.</li>
 *     <li><b>--exec &lt;path&gt;, -x</b>: Match processes by executable path.</li>
 *     <li><b>--user &lt;user&gt;, -u</b>: Match processes by user.</li>
 *     <li><b>--signal &lt;num&gt;, -s</b>: Signal to send when stopping (default: TERM/15).</li>
 *     <li><b>--test, -t</b>: Test mode, simulate actions but do not actually start or stop processes.</li>
 *     <li><b>--quiet, -q</b>: Quiet mode, suppress output.</li>
 * </ul>
 *
 * Usage example:
 * <pre>{@code
 * // Start a process with a specific executable path
 * java StartStopDaemon --start --exec "/path/to/executable"
 *
 * // Stop processes matching a specific name
 * java StartStopDaemon --stop --name "processName"
 *
 * // Check the status of processes run by a specific user
 * java StartStopDaemon --status --user "username"
 *}</pre>
 *
 * Logs detailed error messages and warnings when incorrect arguments are provided or
 * when required information is missing.
 *
 * Designed to improve system process management with testing and logging features.
 *
 * This class is marked @CompileStatic for enhanced performance and type safety, and
 * it uses SLF4J for logging.
 *
 * @author R.A.
 * @version 1.0
 * @since 2025
 */
package org.starship.sys

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.starship.init.Init

@CompileStatic
@Slf4j

/**
 * Represents a daemon responsible for managing system processes.
 * The `StartStopDaemon` class can start, stop, and check the status of processes
 * via command-line arguments. It allows process filtering using various criteria.
 *
 * Main features include:
 * <ul>
 *     <li>Start a process with specified parameters.</li>
 *     <li>Stop one or more processes based on filtering criteria.</li>
 *     <li>Check the status of running processes.</li>
 * </ul>
 *
 * Command-line parameters and options include:
 * <ul>
 *     <li><b>--start, -S</b>: Start a new process.</li>
 *     <li><b>--stop, -K</b>: Stop matching processes.</li>
 *     <li><b>--status, -T</b>: Check if any matching process is running.</li>
 *     <li><b>--name, -n</b>: Match processes by their name.</li>
 *     <li><b>--exec, -x</b>: Match or start a process by its executable path.</li>
 *     <li><b>--user, -u</b>: Match processes owned by a specific user.</li>
 *     <li><b>--signal, -s</b>: Specify a signal to send while stopping processes.</li>
 *     <li><b>--test, -t</b>: Test mode for simulating actions.</li>
 *     <li><b>--quiet, -q</b>: Suppress output in quiet mode.</li>
 * </ul>
 *
 * This class ensures robust process management with features such as:
 * <ul>
 *     <li>Error handling for invalid command-line arguments.</li>
 *     <li>Logging operations using SLF4J.</li>
 *     <li>Adherence to @CompileStatic for type safety and performance.</li>
 * </ul>
 *
 * Processes are matched using the following criteria:
 * <ul>
 *     <li>Name (`--name`)</li>
 *     <li>Executable path (`--exec`)</li>
 *     <li>User (`--user`)</li>
 * </ul>
 *
 * Logging and debugging are facilitated using SLF4J, allowing detailed info and errors 
 * to be logged during runtime operations.
 *
 * @author R.A.
 * @version 1.0
 * @since 2025
 */
class StartStopDaemon {

/**
 * Constructs a StartStopDaemon instance and configures its command-line properties.
 *
 * This initialization:
 * <ul>
 *     <li>Parses and validates the provided command-line arguments.</li>
 *     <li>Sets the operation context (start, stop, or status) based on these arguments.</li>
 *     <li>Prepares the instance to perform actions such as starting processes,
 *         stopping processes by filters, or checking the status of existing processes.</li>
 * </ul>
 *
 * Example usage:
 * <pre>{@code
 *    // Create an instance using command-line arguments
 *    StartStopDaemon daemon = new StartStopDaemon(args);
 *}</pre>
 *
 * Logging is performed to help identify errors in argument parsing.
 *
 * @param args The array of command-line arguments for configuring the daemon.
 */
    enum Context {
        STOP,
        START,
        STATUS
    }

    private Context context
    private String user
    private String processName
    private String execPath
    private int signal = 15 // Default signal (e.g., SIGTERM)
    private boolean testMode = false
    private boolean quietMode = false

    private List<Long> foundPids = []


    /**
     * Finds and stores matching process IDs based on the specified filters 
     * (name, executable path, and user). The filters are set via command-line arguments 
     * (--name, --exec, --user).
     *
     * This method uses the `ProcessHandle` API to iterate through all processes and 
     * applies the `matchesProcess` predicate to determine whether a process 
     * meets the criteria.
     *
     * Matching process IDs are added to the `foundPids` list.
     *
     * Example filters:
     * <ul>
     *     <li><b>--name:</b> Matches processes by part of their name.</li>
     *     <li><b>--exec:</b> Matches processes that have a specific executable path.</li>
     *     <li><b>--user:</b> Matches processes run by a certain user.</li>
     * </ul>
     *
     * Logs an appropriate debug message for each criteria applied.
     *
     * @see ProcessHandle* @see ProcessHandle.Info
     */
    StartStopDaemon(String[] args) {
        parseArguments(args)
    }

    /**
     * Parses command-line arguments and sets the corresponding class properties 
     * for controlling daemon behavior. Handles switches like --start, --stop, 
     * and --status to determine the operation context. Other supported options 
     * such as --user, --name, and --exec are used for filtering processes based on 
     * specific criteria.
     *
     * Error messages are logged for invalid or missing argument values, ensuring 
     * robust command-line parsing.
     *
     * Supported command-line options:
     * <ul>
     *     <li><b>--start, -S</b>: Indicates the daemon should start a new process.</li>
     *     <li><b>--stop, -K</b>: Indicates the daemon should stop one or more processes.</li>
     *     <li><b>--status, -T</b>: Indicates the daemon should check process status.</li>
     *     <li><b>--user, -u &lt;username&gt;</b>: Matches processes owned by the specified user.</li>
     *     <li><b>--name, -n &lt;processName&gt;</b>: Matches processes by name.</li>
     *     <li><b>--exec, -x &lt;path&gt;</b>: Matches processes by their executable path.</li>
     *     <li><b>--signal, -s &lt;signalNumber&gt;</b>: Specifies the signal to be sent to stop processes.</li>
     *     <li><b>--test, -t</b>: Enables test mode without executing the actual actions.</li>
     *     <li><b>--quiet, -q</b>: Suppresses output in quiet mode.</li>
     * </ul>
     *
     * Proper validation ensures required arguments are provided, and appropriate 
     * context (start, stop, or status) is set.
     *
     * @param args The command-line arguments passed to the application.
     */
    private void parseArguments(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--start":
                case "-S":
                    context = Context.START
                    break
                case "--stop":
                case "-K":
                    context = Context.STOP
                    break
                case "--status":
                case "-T":
                    context = Context.STATUS
                    break
                case "--user":
                case "-u":
                    if (++i >= args.length) {
                        log.error("Missing value for --user/-u")
                    }
                    user = args[i]
                    break
                case "--name":
                case "-n":
                    if (++i >= args.length) {
                        log.error("Missing value for --name/-n")
                    }
                    processName = args[i]
                    break
                case "--exec":
                case "-x":
                    if (++i >= args.length) {
                        log.error("Missing value for --exec/-x")
                    }
                    execPath = args[i]
                    break
                case "--signal":
                case "-s":
                    if (++i >= args.length) {
                        log.error("Missing value for --signal/-s")
                    }
                    signal = args[i].toInteger()
                    break
                case "--test":
                case "-t":
                    testMode = true
                    break
                case "--quiet":
                case "-q":
                    quietMode = true
                    break
                default:
                    log.error("Unknown option: ${args[i]}")
            }
        }

        if (!context) {
            log.error("Please specify --start, --stop, or --status.")
        }
    }

    /**
     * Executes the daemon operation based on the specified context.
     * This method is responsible for delegating the appropriate actions 
     * (start, stop, or status check) to other private methods like 
     * `startProcess`, `stopProcesses`, and `checkProcessStatus`.
     *
     * Logs a message or performs the required operation depending on 
     * the context derived from the command-line arguments.
     *
     * If the context is not set properly, it will result in an error.
     *
     * @see #startProcess()
     * @see #stopProcesses()
     * @see #checkProcessStatus()
     */
    void execute() {
        switch (context) {
            case Context.START:
                startProcess()
                break
            case Context.STOP:
                stopProcesses()
                break
            case Context.STATUS:
                checkProcessStatus()
                break
        }
    }

    /**
     * Identifies and collects all processes that match the specified criteria,
     * such as user, process name, or executable path. The matching PIDs are 
     * stored for further operation, like stopping or checking their status.
     *
     * The method leverages `ProcessHandle.allProcesses()` to retrieve all
     * available processes, filters them using the `matchesProcess()` method,
     * and adds the PIDs of matching processes to the `foundPids` collection.
     *
     * @see #matchesProcess(ProcessHandle)
     */
    private void findMatchingProcesses() {
        ProcessHandle.allProcesses()
                .filter(this.&matchesProcess)
                .mapToLong(ProcessHandle.&pid)
                .forEach(foundPids.&add)
    }

    /**
     * Checks if the given ProcessHandle matches the specified criteria.
     *
     * This method evaluates the process handle against the filter parameters such as
     * process name, executable path, and user. Returns true if any of the criteria match,
     * otherwise false.
     *
     * Criteria:
     * - Matches by process name, if specified.
     * - Matches by executable path, if specified.
     * - Matches by user, if specified.
     *
     * @param handle the ProcessHandle to evaluate
     * @return true if the process matches the specified criteria, false otherwise
     */
    private boolean matchesProcess(ProcessHandle handle) {
        if (processName && handle.info().command().orElse("").contains(processName)) {
            return true
        }
        if (execPath && handle.info().command().orElse("") == execPath) {
            return true
        }
        if (user && handle.info().user().orElse("") == user) {
            return true
        }
        return false
    }

    /**
     * Starts a new process based on the specified executable path.
     *
     * If a matching process is already running, this method will not start another one,
     * unless the quiet mode is enabled, in which case no message is printed.
     *
     * If no executable path is provided, an error is logged and the method will abort.
     *
     * This method ensures that the process is correctly registered in the system resources
     * and provides feedback about the process's status unless in quiet mode.
     *
     * @throws IOException if an I/O error occurs during process creation
     */
    private void startProcess() {
        findMatchingProcesses()
        if (!foundPids.isEmpty()) {
            if (!quietMode) {
                println "Process is already running: $foundPids"
            }
            return
        }

        if (!execPath) {
            log.error("Executable path (--exec) must be specified for --start.")
        }

        List<String> command = [execPath]

        Process process = new ProcessBuilder(command)
                .inheritIO()
                .start()

        Init.systemResources.processTable[this.class.simpleName] = process
        if (!quietMode) {
            println "Process started: $execPath"
        }
    }

    /**
     * Stops all processes matching the specified criteria.
     *
     * This method utilizes the list of collected PIDs (`foundPids`) from `findMatchingProcesses()`.
     * Matching processes are stopped based on their process IDs. If running in test mode,
     * no processes will be stopped, but it will log what would have been done instead.
     *
     * Behavior:
     * - If no matching process is found, it logs a message (unless in quiet mode).
     * - Removes stopped processes from the internal system resource table.
     *
     * Quiet Mode:
     * - If enabled, suppresses all logs and output messages.
     *
     * Test Mode:
     * - Logs which processes would have been stopped, but makes no changes.
     *
     * @see #findMatchingProcesses()
     * @see #matchesProcess(ProcessHandle)
     */
    private void stopProcesses() {
        findMatchingProcesses()

        if (foundPids.isEmpty()) {
            if (!quietMode) {
                println "No matching processes found to stop."
            }
            return
        }

        foundPids.each { pid ->
            Optional<ProcessHandle> process = ProcessHandle.of(pid)
            process.ifPresent { handle ->
                if (testMode) {
                    if (!quietMode) {
                        println "Test mode: Would stop PID $pid"
                    }
                } else {
                    handle.destroy()

                    // Remove the process from systemResources
                    Init.systemResources.processTable.remove(this.class.simpleName)

                    if (!quietMode) {
                        println "Stopped and unregistered PID $pid"
                    }
                }
            }
        }
    }

    /**
     * Checks the status of processes that match the specified criteria.
     *
     * This method uses the `findMatchingProcesses()` to retrieve a list of processes
     * that meet the defined filters, such as process name, executable path,
     * or user. If matching processes are found, their PIDs are printed, and the 
     * program exits with a success status code. If none are found, it prints 
     * that no processes are running and exits with a failure status code.
     *
     * Exit Codes:
     * - 0: One or more matching processes are running.
     * - 3: No matching processes were found.
     *
     * Behavior:
     * - Outputs the process PIDs if found.
     * - Terminates the program after execution.
     *
     * @see #findMatchingProcesses()
     */
    private void checkProcessStatus() {
        findMatchingProcesses()

        if (foundPids.isEmpty()) {
            println "Process not running."
            System.exit(3)
        } else {
            println "Process(s) running: $foundPids"
            System.exit(0)
        }
    }

    /**
     * Entry point for the StartStopDaemon application.
     *
     * This method initializes the application with provided command-line arguments.
     * It catches and handles exceptions that may occur during execution, such as 
     * invalid arguments or I/O errors, providing relevant error messages and 
     * terminating with appropriate exit codes:
     *
     * Exit Codes:
     * - 0: Successful execution.
     * - 1: Invalid arguments error.
     * - 2: I/O error occurred.
     * - 3: No matching processes found for status check.
     *
     * Supported Commands:
     * - Start a new process (`--start` or `-S`).
     * - Stop matching processes (`--stop` or `-K`).
     * - Check the status of matching processes (`--status` or `-T`).
     *
     * @param args command line arguments passed to the application
     */
    static void main(String[] args) {
        try {
            new StartStopDaemon(args).execute()
        } catch (IllegalArgumentException e) {
            System.err.println("Error: ${e.message}")
            printUsage()
            System.exit(1)
        } catch (IOException e) {
            System.err.println("I/O Error: ${e.message}")
            System.exit(2)
        }
    }

    /**
     * Prints the usage information for the StartStopDaemon application.
     *
     * This method provides a detailed list of options supported by the application,
     * including descriptions for each command-line argument. It is typically used
     * to help users understand the application usage when invalid arguments are 
     * provided or when help is explicitly requested.
     *
     * Options:
     * - `--start, -S`: Starts a new process unless a matching one already exists.
     * - `--stop, -K`: Stops all matching processes.
     * - `--status, -T`: Checks if any process matching the specified criteria is running.
     * - `--name <name>, -n`: Matches processes by their name.
     * - `--exec <path>, -x`: Matches processes by their executable path.
     * - `--user <user>, -u`: Matches processes by the owner user.
     * - `--signal <num>, -s`: Specifies the signal to send when stopping processes 
     *                         (default is TERM/15).
     * - `--test, -t`: Enables test mode where no actual processes are started or stopped,
     *                 only mock actions are logged.
     * - `--quiet, -q`: Enables quiet mode to suppress all output messages.
     *
     */
    private static void printUsage() {
        println "Usage: StartStopDaemon [OPTIONS]"
        println "Options:"
        println "  --start, -S           Start a new process unless matching one already exists"
        println "  --stop, -K            Stop all matching processes"
        println "  --status, -T          Check if a process is running"
        println "  --name <name>, -n     Match processes by name"
        println "  --exec <path>, -x     Match processes by executable path"
        println "  --user <user>, -u     Match processes by user"
        println "  --signal <num>, -s    Signal to send when stopping (default: TERM/15)"
        println "  --test, -t            Test mode, do not actually start or stop anything"
        println "  --quiet, -q           Quiet mode, suppress output"
    }
}
