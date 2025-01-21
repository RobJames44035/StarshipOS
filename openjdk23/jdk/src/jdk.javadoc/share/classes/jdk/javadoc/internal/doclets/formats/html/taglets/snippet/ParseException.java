/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package jdk.javadoc.internal.doclets.formats.html.taglets.snippet;

import java.util.function.Supplier;

/**
 * An exception thrown by {@link Parser} and {@link MarkupParser}.
 *
 * This exception is only used to capture a user-facing error message.
 * The message supplier is accepted not to control when to obtain a message,
 * but to abstract how to obtain it.
 */
public class ParseException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 1;

    private final int index;

    public ParseException(Supplier<String> messageSupplier, int position) {
        super(messageSupplier.get());
        if (position < 0) {
            throw new IllegalArgumentException(String.valueOf(position));
        }
        this.index = position;
    }

    public int getPosition() {
        return index;
    }
}
