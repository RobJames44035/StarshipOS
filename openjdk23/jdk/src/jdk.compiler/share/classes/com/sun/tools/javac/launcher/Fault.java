/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package com.sun.tools.javac.launcher;

import com.sun.tools.javac.util.JCDiagnostic.Error;

import java.io.Serial;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * A runtime exception used to report errors.
 *
 * <p><strong>This is NOT part of any supported API.
 * If you write code that depends on this, you do so at your own
 * risk.  This code and its internal interfaces are subject to change
 * or deletion without notice.</strong></p>
 */
public final class Fault extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 2L;

    private static final String BUNDLE_NAME = "com.sun.tools.javac.resources.launcher";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    private static final String ERROR_PREFIX = RESOURCE_BUNDLE.getString("launcher.error");

    /**
     * Returns a localized string from a resource bundle.
     *
     * @param error the error for which to get the localized text
     * @return the localized string
     */
    private static String getMessage(Error error) {
        String key = error.key();
        Object[] args = error.getArgs();
        try {
            String resource = RESOURCE_BUNDLE.getString(key);
            String message = MessageFormat.format(resource, args);
            return ERROR_PREFIX + message;
        } catch (MissingResourceException e) {
            return "Cannot access resource; " + key + Arrays.toString(args);
        }
    }

    Fault(Error error) {
        super(getMessage(error));
    }
}
