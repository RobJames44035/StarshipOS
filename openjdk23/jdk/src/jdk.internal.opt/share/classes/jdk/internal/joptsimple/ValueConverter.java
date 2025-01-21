/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package jdk.internal.joptsimple;

/**
 * Instances of this interface are used to convert arguments of options into specific Java types.
 *
 * @param <V> constraint on the type of values being converted to
 * @author <a href="mailto:pholser@alumni.rice.edu">Paul Holser</a>
 */
public interface ValueConverter<V> {
    /**
     * Converts the given string value into a Java type.
     *
     * @param value the string to convert
     * @return the converted value
     * @throws ValueConversionException if a problem occurs while converting the value
     */
    V convert( String value );

    /**
     * Gives the class of the type of values this converter converts to.
     *
     * @return the target class for conversion
     */
    Class<? extends V> valueType();

    /**
     * Gives a string that describes the pattern of the values this converter expects, if any.  For example, a date
     * converter can respond with a {@link java.text.SimpleDateFormat date format string}.
     *
     * @return a value pattern, or {@code null} if there's nothing interesting here
     */
    String valuePattern();
}
