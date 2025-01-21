/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package jdk.internal.joptsimple.internal;

import java.lang.reflect.Method;

import jdk.internal.joptsimple.ValueConverter;

import static jdk.internal.joptsimple.internal.Reflection.*;

/**
 * @param <V> constraint on the type of values being converted to
 * @author <a href="mailto:pholser@alumni.rice.edu">Paul Holser</a>
 */
class MethodInvokingValueConverter<V> implements ValueConverter<V> {
    private final Method method;
    private final Class<V> clazz;

    MethodInvokingValueConverter( Method method, Class<V> clazz ) {
        this.method = method;
        this.clazz = clazz;
    }

    public V convert( String value ) {
        return clazz.cast( invoke( method, value ) );
    }

    public Class<V> valueType() {
        return clazz;
    }

    public String valuePattern() {
        return null;
    }
}
