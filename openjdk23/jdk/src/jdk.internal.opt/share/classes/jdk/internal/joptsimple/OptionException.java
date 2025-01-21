/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package jdk.internal.joptsimple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import jdk.internal.joptsimple.internal.Strings;

import static java.util.Collections.*;
import static jdk.internal.joptsimple.internal.Messages.*;

/**
 * Thrown when a problem occurs during option parsing.
 *
 * @author <a href="mailto:pholser@alumni.rice.edu">Paul Holser</a>
 */
public abstract class OptionException extends RuntimeException {
    private static final long serialVersionUID = -1L;

    @SuppressWarnings("serial") // Type of field is not Serializable
    private final List<String> options = new ArrayList<>();

    protected OptionException( List<String> options ) {
        this.options.addAll( options );
    }

    protected OptionException( Collection<? extends OptionSpec<?>> options ) {
        this.options.addAll( specsToStrings( options ) );
    }

    protected OptionException( Collection<? extends OptionSpec<?>> options, Throwable cause ) {
        super( cause );
        this.options.addAll( specsToStrings( options ) );
    }

    private List<String> specsToStrings( Collection<? extends OptionSpec<?>> options ) {
        List<String> strings = new ArrayList<>();
        for ( OptionSpec<?> each : options )
            strings.add( specToString( each ) );
        return strings;
    }

    private String specToString( OptionSpec<?> option ) {
        return Strings.join( new ArrayList<>( option.options() ), "/" );
    }

    /**
     * Gives the option being considered when the exception was created.
     *
     * @return the option being considered when the exception was created
     */
    public List<String> options() {
        return unmodifiableList( options );
    }

    protected final String singleOptionString() {
        return singleOptionString( options.get( 0 ) );
    }

    protected final String singleOptionString( String option ) {
        return option;
    }

    protected final String multipleOptionString() {
        StringBuilder buffer = new StringBuilder( "[" );

        Set<String> asSet = new LinkedHashSet<String>( options );
        for ( Iterator<String> iter = asSet.iterator(); iter.hasNext(); ) {
            buffer.append( singleOptionString(iter.next()) );
            if ( iter.hasNext() )
                buffer.append( ", " );
        }

        buffer.append( ']' );

        return buffer.toString();
    }

    static OptionException unrecognizedOption( String option ) {
        return new UnrecognizedOptionException( option );
    }

    @Override
    public final String getMessage() {
        return localizedMessage( Locale.getDefault() );
    }

    final String localizedMessage( Locale locale ) {
        return formattedMessage( locale );
    }

    private String formattedMessage( Locale locale ) {
        return message( locale, "jdk.internal.joptsimple.ExceptionMessages", getClass(), "message", messageArguments() );
    }

    abstract Object[] messageArguments();
}
