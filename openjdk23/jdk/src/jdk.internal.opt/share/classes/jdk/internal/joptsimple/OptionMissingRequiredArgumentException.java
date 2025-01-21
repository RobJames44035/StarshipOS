/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package jdk.internal.joptsimple;

import static java.util.Arrays.*;

/**
 * Thrown when the option parser discovers options that require an argument, but are missing an argument.
 *
 * @author <a href="mailto:pholser@alumni.rice.edu">Paul Holser</a>
 */
class OptionMissingRequiredArgumentException extends OptionException {
    private static final long serialVersionUID = -1L;

    OptionMissingRequiredArgumentException( OptionSpec<?> option ) {
        super( asList( option ) );
    }

    @Override
    Object[] messageArguments() {
        return new Object[] { singleOptionString() };
    }
}
