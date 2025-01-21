/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package jdk.internal.joptsimple.util;

import java.util.Locale;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;
import static jdk.internal.joptsimple.internal.Messages.message;

import jdk.internal.joptsimple.ValueConversionException;
import jdk.internal.joptsimple.ValueConverter;

/**
 * Ensures that values entirely match a regular expression.
 *
 * @author <a href="mailto:pholser@alumni.rice.edu">Paul Holser</a>
 */
public class RegexMatcher implements ValueConverter<String> {
    private final Pattern pattern;

    /**
     * Creates a matcher that uses the given regular expression, modified by the given flags.
     *
     * @param pattern the regular expression pattern
     * @param flags modifying regex flags
     * @throws IllegalArgumentException if bit values other than those corresponding to the defined match flags are
     * set in {@code flags}
     * @throws java.util.regex.PatternSyntaxException if the expression's syntax is invalid
     */
    public RegexMatcher( String pattern, int flags ) {
        this.pattern = compile( pattern, flags );
    }

    /**
     * Gives a matcher that uses the given regular expression.
     *
     * @param pattern the regular expression pattern
     * @return the new converter
     * @throws java.util.regex.PatternSyntaxException if the expression's syntax is invalid
     */
    public static ValueConverter<String> regex( String pattern ) {
        return new RegexMatcher( pattern, 0 );
    }

    public String convert( String value ) {
        if ( !pattern.matcher( value ).matches() ) {
            raiseValueConversionFailure( value );
        }

        return value;
    }

    public Class<String> valueType() {
        return String.class;
    }

    public String valuePattern() {
        return pattern.pattern();
    }

    private void raiseValueConversionFailure( String value ) {
        String message = message(
            Locale.getDefault(),
            "jdk.internal.joptsimple.ExceptionMessages",
            RegexMatcher.class,
            "message",
            value,
            pattern.pattern() );
        throw new ValueConversionException( message );
    }
}
