/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package org.openjdk.bench.java.lang.foreign;

import jdk.internal.misc.Unsafe;

import java.lang.reflect.Field;

public class Utils {

    public static final Unsafe unsafe;

    //setup unsafe
    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException();
        }
    }
}
