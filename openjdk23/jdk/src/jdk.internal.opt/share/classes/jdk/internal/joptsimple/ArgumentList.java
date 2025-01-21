/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package jdk.internal.joptsimple;

import static jdk.internal.joptsimple.ParserRules.*;

/**
 * <p>Wrapper for an array of command line arguments.</p>
 *
 * @author <a href="mailto:pholser@alumni.rice.edu">Paul Holser</a>
 */
class ArgumentList {
    private final String[] arguments;
    private int currentIndex;

    ArgumentList( String... arguments ) {
        this.arguments = arguments.clone();
    }

    boolean hasMore() {
        return currentIndex < arguments.length;
    }

    String next() {
        return arguments[ currentIndex++ ];
    }

    String peek() {
        return arguments[ currentIndex ];
    }

    void treatNextAsLongOption() {
        if ( HYPHEN_CHAR != arguments[ currentIndex ].charAt( 0 ) )
            arguments[ currentIndex ] = DOUBLE_HYPHEN + arguments[ currentIndex ];
    }
}
