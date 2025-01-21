/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package jdk.jpackage.internal.util.function;

import java.lang.reflect.InvocationTargetException;

public class ExceptionBox extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static RuntimeException rethrowUnchecked(Throwable throwable) {
        if (throwable instanceof RuntimeException err) {
            throw err;
        }

        if (throwable instanceof Error err) {
            throw err;
        }

        if (throwable instanceof InvocationTargetException err) {
            throw rethrowUnchecked(err.getCause());
        }

        throw new ExceptionBox(throwable);
    }

    private ExceptionBox(Throwable throwable) {
        super(throwable);
    }
}
