/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.util;

import java.util.Locale;
import java.util.MissingResourceException;

/**
 * This interface provides a generic message formatting mechanism and
 * is useful for producing messages that must be localed and/or formatted
 * with replacement text.
 *
 * @see com.sun.org.apache.xerces.internal.impl.XMLErrorReporter
 *
 * @author Andy Clark
 *
 */
public interface MessageFormatter {

    //
    // MessageFormatter methods
    //

    /**
     * Formats a message with the specified arguments using the given
     * locale information.
     *
     * @param locale    The locale of the message.
     * @param key       The message key.
     * @param arguments The message replacement text arguments. The order
     *                  of the arguments must match that of the placeholders
     *                  in the actual message.
     *
     * @return Returns the formatted message.
     *
     * @throws MissingResourceException Thrown if the message with the
     *                                  specified key cannot be found.
     */
    public String formatMessage(Locale locale, String key, Object[] arguments)
        throws MissingResourceException;

} // interface MessageFormatter
