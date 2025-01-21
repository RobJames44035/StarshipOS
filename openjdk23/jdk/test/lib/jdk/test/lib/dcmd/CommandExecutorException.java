/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.dcmd;

/**
 * CommandExecutorException encapsulates exceptions thrown (on the "calling side") from the execution of Diagnostic
 * Commands
 */
public class CommandExecutorException extends RuntimeException {
    private static final long serialVersionUID = -7039597746579144280L;

    public CommandExecutorException(String message, Throwable e) {
        super(message, e);
    }
}
