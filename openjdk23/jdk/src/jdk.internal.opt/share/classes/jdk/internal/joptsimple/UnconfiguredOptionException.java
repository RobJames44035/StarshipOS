/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package jdk.internal.joptsimple;

import java.util.List;

import static java.util.Collections.*;

/**
 * Thrown when an option parser refers to an option that is not in fact configured already on the parser.
 *
 * @author <a href="mailto:pholser@alumni.rice.edu">Paul Holser</a>
 */
class UnconfiguredOptionException extends OptionException {
    private static final long serialVersionUID = -1L;

    UnconfiguredOptionException( String option ) {
        this( singletonList( option ) );
    }

    UnconfiguredOptionException( List<String> options ) {
        super( options );
    }

    @Override
    Object[] messageArguments() {
        return new Object[] { multipleOptionString() };
    }
}
