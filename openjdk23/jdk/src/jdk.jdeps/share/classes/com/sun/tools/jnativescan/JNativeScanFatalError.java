/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package com.sun.tools.jnativescan;

import java.io.Serial;

// Exception used in case of fatal error that is reasonably expected and handled.
public class JNativeScanFatalError extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public JNativeScanFatalError(String message) {
        super(message);
    }

    public JNativeScanFatalError(String message, Throwable cause) {
        super(message, cause);
    }

    public JNativeScanFatalError(Throwable cause) {
        super(cause);
    }
}
