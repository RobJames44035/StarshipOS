/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package jdk.internal.joptsimple.internal;

import java.util.Iterator;

import static java.lang.System.*;
import static java.util.Arrays.*;

/**
 * @author <a href="mailto:pholser@alumni.rice.edu">Paul Holser</a>
 */
public final class Strings {
    public static final String EMPTY = "";
    public static final String LINE_SEPARATOR = getProperty( "line.separator" );

    private Strings() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gives a string consisting of the given character repeated the given number of times.
     *
     * @param ch the character to repeat
     * @param count how many times to repeat the character
     * @return the resultant string
     */
    public static String repeat( char ch, int count ) {
        StringBuilder buffer = new StringBuilder();

        for ( int i = 0; i < count; ++i )
            buffer.append( ch );

        return buffer.toString();
    }

    /**
     * Tells whether the given string is either {@code} or consists solely of whitespace characters.
     *
     * @param target string to check
     * @return {@code true} if the target string is null or empty
     */
    public static boolean isNullOrEmpty( String target ) {
        return target == null || target.isEmpty();
    }


    /**
     * Gives a string consisting of a given string prepended and appended with surrounding characters.
     *
     * @param target a string
     * @param begin character to prepend
     * @param end character to append
     * @return the surrounded string
     */
    public static String surround( String target, char begin, char end ) {
        return begin + target + end;
    }

    /**
     * Gives a string consisting of the elements of a given array of strings, each separated by a given separator
     * string.
     *
     * @param pieces the strings to join
     * @param separator the separator
     * @return the joined string
     */
    public static String join( String[] pieces, String separator ) {
        return join( asList( pieces ), separator );
    }

    /**
     * Gives a string consisting of the string representations of the elements of a given array of objects,
     * each separated by a given separator string.
     *
     * @param pieces the elements whose string representations are to be joined
     * @param separator the separator
     * @return the joined string
     */
    public static String join( Iterable<String> pieces, String separator ) {
        StringBuilder buffer = new StringBuilder();

        for ( Iterator<String> iter = pieces.iterator(); iter.hasNext(); ) {
            buffer.append( iter.next() );

            if ( iter.hasNext() )
                buffer.append( separator );
        }

        return buffer.toString();
    }
}
