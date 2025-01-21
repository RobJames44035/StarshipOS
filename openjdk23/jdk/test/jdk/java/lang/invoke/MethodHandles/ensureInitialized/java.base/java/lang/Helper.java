/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package java.lang;

import jdk.internal.misc.Unsafe;

public class Helper {
    private static final Unsafe UNSAFE = Unsafe.getUnsafe();
    public static boolean isInitialized(Class<?> c) {
        return !UNSAFE.shouldBeInitialized(c);
    }
}
