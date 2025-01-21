/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package jdk.internal.joptsimple.internal;

import java.lang.reflect.Constructor;

import jdk.internal.joptsimple.ValueConverter;

import static jdk.internal.joptsimple.internal.Reflection.*;

/**
 * @param <V> constraint on the type of values being converted to
 * @author <a href="mailto:pholser@alumni.rice.edu">Paul Holser</a>
 */
class ConstructorInvokingValueConverter<V> implements ValueConverter<V> {
    private final Constructor<V> ctor;

    ConstructorInvokingValueConverter( Constructor<V> ctor ) {
        this.ctor = ctor;
    }

    public V convert( String value ) {
        return instantiate( ctor, value );
    }

    public Class<V> valueType() {
        return ctor.getDeclaringClass();
    }

    public String valuePattern() {
        return null;
    }
}
