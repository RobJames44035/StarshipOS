/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.javadoc.internal.tool;

import static jdk.javadoc.internal.tool.Main.Result.CMDERR;

/**
 * Provides a mechanism for the javadoc tool to indicate an option
 * decoding issue, arising from a command-line error.
 */

class IllegalOptionValue extends OptionException {

    private static final long serialVersionUID = 0;

    /**
     * Constructs an object containing a runnable and a message.
     * @param method a method to display suitable usage text
     * @param message the detailed message
     */
    IllegalOptionValue(Runnable method, String message) {
        super(CMDERR, method, message);
    }
}
