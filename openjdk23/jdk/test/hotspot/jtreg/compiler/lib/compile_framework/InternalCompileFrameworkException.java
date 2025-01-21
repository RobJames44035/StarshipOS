/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.lib.compile_framework;

/**
 * Internal exception thrown in Compilation Framework. Most likely, this is due to a bug in the CompileFramework.
 */
public class InternalCompileFrameworkException extends RuntimeException {
    public InternalCompileFrameworkException(String message) {
        super("Internal exception in Compile Framework, please file a bug:" + System.lineSeparator() + message);
    }

    public InternalCompileFrameworkException(String message, Throwable e) {
        super("Internal exception in Compile Framework, please file a bug:" + System.lineSeparator() + message, e);
    }
}
