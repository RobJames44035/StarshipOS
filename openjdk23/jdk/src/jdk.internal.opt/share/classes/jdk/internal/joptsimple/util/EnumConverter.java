/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package jdk.internal.joptsimple.util;

import java.text.MessageFormat;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.ResourceBundle;

import jdk.internal.joptsimple.ValueConversionException;
import jdk.internal.joptsimple.ValueConverter;

/**
 * Converts values to {@link java.lang.Enum}s.
 *
 * @author <a href="mailto:christian.ohr@gmail.com">Christian Ohr</a>
 */
public abstract class EnumConverter<E extends Enum<E>> implements ValueConverter<E> {
    private final Class<E> clazz;

    private String delimiters = "[,]";

    /**
     * This constructor must be called by subclasses, providing the enum class as the parameter.
     *
     * @param clazz enum class
     */
    protected EnumConverter( Class<E> clazz ) {
        this.clazz = clazz;
    }

    @Override
    public E convert( String value ) {
        for ( E each : valueType().getEnumConstants() ) {
            if ( each.name().equalsIgnoreCase( value ) ) {
                return each;
            }
        }

        throw new ValueConversionException( message( value ) );
    }

    @Override
    public Class<E> valueType() {
        return clazz;
    }

    /**
     * Sets the delimiters for the message string. Must be a 3-letter string,
     * where the first character is the prefix, the second character is the
     * delimiter between the values, and the 3rd character is the suffix.
     *
     * @param delimiters delimiters for message string. Default is [,]
     */
    public void setDelimiters( String delimiters ) {
        this.delimiters = delimiters;
    }

    @Override
    public String valuePattern() {
        EnumSet<E> values = EnumSet.allOf( valueType() );

        StringBuilder builder = new StringBuilder();
        builder.append( delimiters.charAt(0) );
        for ( Iterator<E> i = values.iterator(); i.hasNext(); ) {
            builder.append( i.next().toString() );
            if ( i.hasNext() )
                builder.append( delimiters.charAt( 1 ) );
        }
        builder.append( delimiters.charAt( 2 ) );

        return builder.toString();
    }

    private String message( String value ) {
        ResourceBundle bundle = ResourceBundle.getBundle( "jdk.internal.joptsimple.ExceptionMessages" );
        Object[] arguments = new Object[] { value, valuePattern() };
        String template = bundle.getString( EnumConverter.class.getName() + ".message" );
        return new MessageFormat( template ).format( arguments );
    }
}
