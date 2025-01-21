/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.vectorapi.reshape.utils;

import jdk.internal.misc.Unsafe;

/**
 * Unsafe to check for correctness of reinterpret operations. May be replaced with foreign API later.
 */
public class UnsafeUtils {
    private static final Unsafe UNSAFE = Unsafe.getUnsafe();

    public static long arrayBase(Class<?> etype) {
        return UNSAFE.arrayBaseOffset(etype.arrayType());
    }

    public static byte getByte(Object o, long base, int i) {
        // This technically leads to UB, what we need is UNSAFE.getByteUnaligned but they seem to be equivalent
        return UNSAFE.getByte(o, base + (long)i * Unsafe.ARRAY_BYTE_INDEX_SCALE);
    }

    public static void putByte(Object o, long base, int i, byte value) {
        // This technically leads to UB, what we need is UNSAFE.putByteUnaligned but they seem to be equivalent
        UNSAFE.putByte(o, base + (long)i * Unsafe.ARRAY_BYTE_INDEX_SCALE, value);
    }

    public static short getShort(Object o, long base, int i) {
        return UNSAFE.getShort(o, base + (long)i * Unsafe.ARRAY_SHORT_INDEX_SCALE);
    }

    public static void putShort(Object o, long base, int i, short value) {
        UNSAFE.putShort(o, base + (long)i * Unsafe.ARRAY_SHORT_INDEX_SCALE, value);
    }

    public static int getInt(Object o, long base, int i) {
        return UNSAFE.getInt(o, base + (long)i * Unsafe.ARRAY_INT_INDEX_SCALE);
    }

    public static void putInt(Object o, long base, int i, int value) {
        UNSAFE.putInt(o, base + (long)i * Unsafe.ARRAY_INT_INDEX_SCALE, value);
    }

    public static long getLong(Object o, long base, int i) {
        return UNSAFE.getLong(o, base + (long)i * Unsafe.ARRAY_LONG_INDEX_SCALE);
    }

    public static void putLong(Object o, long base, int i, long value) {
        UNSAFE.putLong(o, base + (long)i * Unsafe.ARRAY_LONG_INDEX_SCALE, value);
    }

    public static float getFloat(Object o, long base, int i) {
        return UNSAFE.getFloat(o, base + (long)i * Unsafe.ARRAY_FLOAT_INDEX_SCALE);
    }

    public static void putFloat(Object o, long base, int i, float value) {
        UNSAFE.putFloat(o, base + (long)i * Unsafe.ARRAY_FLOAT_INDEX_SCALE, value);
    }

    public static double getDouble(Object o, long base, int i) {
        return UNSAFE.getDouble(o, base + (long)i * Unsafe.ARRAY_DOUBLE_INDEX_SCALE);
    }

    public static void putDouble(Object o, long base, int i, double value) {
        UNSAFE.putDouble(o, base + (long)i * Unsafe.ARRAY_DOUBLE_INDEX_SCALE, value);
    }
}
