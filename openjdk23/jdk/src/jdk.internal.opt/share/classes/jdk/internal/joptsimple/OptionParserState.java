/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package jdk.internal.joptsimple;

import static jdk.internal.joptsimple.ParserRules.*;

/**
 * Abstraction of parser state; mostly serves to model how a parser behaves depending on whether end-of-options
 * has been detected.
 *
 * @author <a href="mailto:pholser@alumni.rice.edu">Paul Holser</a>
 */
abstract class OptionParserState {
    static OptionParserState noMoreOptions() {
        return new OptionParserState() {
            @Override
            protected void handleArgument( OptionParser parser, ArgumentList arguments, OptionSet detectedOptions ) {
                parser.handleNonOptionArgument( arguments.next(), arguments, detectedOptions );
            }
        };
    }

    static OptionParserState moreOptions( final boolean posixlyCorrect ) {
        return new OptionParserState() {
            @Override
            protected void handleArgument( OptionParser parser, ArgumentList arguments, OptionSet detectedOptions ) {
                String candidate = arguments.next();
                try {
                    if ( isOptionTerminator( candidate ) ) {
                        parser.noMoreOptions();
                        return;
                    } else if ( isLongOptionToken( candidate ) ) {
                        parser.handleLongOptionToken( candidate, arguments, detectedOptions );
                        return;
                    } else if ( isShortOptionToken( candidate ) ) {
                        parser.handleShortOptionToken( candidate, arguments, detectedOptions );
                        return;
                    }
                } catch ( UnrecognizedOptionException e ) {
                    if ( !parser.doesAllowsUnrecognizedOptions() )
                        throw e;
                }

                if ( posixlyCorrect )
                    parser.noMoreOptions();

                parser.handleNonOptionArgument( candidate, arguments, detectedOptions );
            }
        };
    }

    protected abstract void handleArgument( OptionParser parser, ArgumentList arguments, OptionSet detectedOptions );
}
