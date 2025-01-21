/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package jdk.internal.joptsimple.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

import jdk.internal.joptsimple.ValueConversionException;
import jdk.internal.joptsimple.ValueConverter;
import jdk.internal.joptsimple.internal.Messages;

/**
 * Converts values to {@link java.net.InetAddress} using {@link InetAddress#getByName(String) getByName}.
 *
 * @author <a href="mailto:r@ymund.de">Raymund F\u00FCl\u00F6p</a>
 */
public class InetAddressConverter implements ValueConverter<InetAddress> {
    public InetAddress convert( String value ) {
        try {
            return InetAddress.getByName( value );
        }
        catch ( UnknownHostException e ) {
            throw new ValueConversionException( message( value ) );
        }
    }

    public Class<InetAddress> valueType() {
        return InetAddress.class;
    }

    public String valuePattern() {
        return null;
    }

    private String message( String value ) {
        return Messages.message(
            Locale.getDefault(),
            "jdk.internal.joptsimple.ExceptionMessages",
            InetAddressConverter.class,
            "message",
            value );
    }
}
