/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package jdk.internal.joptsimple;

import java.util.List;

/**
 * Thrown when options marked as allowed are specified on the command line, but the options they depend upon are
 * present/not present.
 */
class UnavailableOptionException extends OptionException {
    private static final long serialVersionUID = -1L;

    UnavailableOptionException( List<? extends OptionSpec<?>> forbiddenOptions ) {
        super( forbiddenOptions );
    }

    @Override
    Object[] messageArguments() {
        return new Object[] { multipleOptionString() };
    }
}
