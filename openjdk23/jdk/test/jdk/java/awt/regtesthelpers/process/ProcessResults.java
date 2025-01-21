/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package test.java.awt.regtesthelpers.process;

import java.io.PrintStream;

/**
 * The calss incapsulates process related information and methods to
 * handle this information.
 *
 */

public class ProcessResults {
    private int exitValue;
    private StringBuilder stdout;
    private StringBuilder stderr;

    public ProcessResults() {
        exitValue = -1;
        stdout = new StringBuilder();
        stderr = new StringBuilder();
    }

    public synchronized int getExitValue () {
        return exitValue;
    }

    public synchronized String getStdOut() {
        return stdout.toString();
    }

   public synchronized String getStdErr() {
        return stderr.toString();
    }

    /** Prints child process standart output into the desired PrintStream.
     *
     * @param printTo PrintStraem where output have to be redirected
     */
    public synchronized void printProcessStandartOutput(PrintStream printTo) {
        if (stdout != null && stdout.length() > 0) {
            printTo.println("========= Child VM System.out ========");
            printTo.print(stdout);
            printTo.println("======================================");
        }
    }

    /** Prints child process error output into the desired PrintStream.
     *
     * @param printTo PrintStraem where output have to be redirected
     */
    public synchronized void printProcessErrorOutput(PrintStream printTo) {
        if (stderr != null && stderr.length() > 0) {
            printTo.println("========= Child VM System.err ========");
            printTo.print(stderr);
            printTo.println("======================================");
        }
    }

    /**  Prints child process error output into the desired {@code PrintStream},
     *   if child JVM error output contains any of the next words: "error",
     *   "exception". We cannot be sure that the test is failed when stderr is
     *   not empty, because in error stream could be written some debug information.
     *
     * @param err PrintStraem where output have to be redirected
     */
    public synchronized void verifyStdErr(PrintStream err) {
        if (stderr != null && ((stderr.toString().toLowerCase().indexOf("error") != -1)
                || (stderr.toString().toLowerCase().indexOf("exception") != -1)))
        {
            printProcessErrorOutput(err);
            throw new RuntimeException("WARNING: Child process  error stream " +
                    "is not empty!");
        }
    }

    /**  Throws new RuntimeException if the child JVM returns not 0 value.
     *
     * @param err PrintStraem where output have to be redirected
     */
    public synchronized void verifyProcessExitValue(PrintStream err) {
        if (exitValue != 0) {
            throw new RuntimeException("Child process returns not 0 value!" +
                    "Returned value is " + exitValue);
        }
    }

    public void verifyProcessExecutionResults(PrintStream err) {
        // the next functions are synchronized
        verifyStdErr(err);
        verifyProcessExitValue(err);
    }

    synchronized void appendToStdOut(char c) {
        stdout.append(c);
    }

    synchronized void appendToStdErr(char c) {
        stderr.append(c);
    }

    synchronized void setExitValue(int exitValue) {
        this.exitValue = exitValue;
    }
}
