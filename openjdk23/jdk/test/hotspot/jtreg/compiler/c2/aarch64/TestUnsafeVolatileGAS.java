/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package compiler.c2.aarch64;

import java.lang.reflect.Field;
import jdk.internal.misc.Unsafe;

class TestUnsafeVolatileGAS
{
    public volatile int f_int = -1;
    public volatile Integer f_obj = Integer.valueOf(-1);
    public volatile long f_long = -1;

    public static Unsafe unsafe = Unsafe.getUnsafe();
    public static Field f_int_field;
    public static Field f_obj_field;
    public static Field f_long_field;
    public static long f_int_off;
    public static long f_obj_off;
    public static long f_long_off;

    static {
        try {
            f_int_field = TestUnsafeVolatileGAS.class.getField("f_int");
            f_obj_field = TestUnsafeVolatileGAS.class.getField("f_obj");
            f_long_field = TestUnsafeVolatileGAS.class.getField("f_long");
            f_int_off = unsafe.objectFieldOffset(f_int_field);
            f_obj_off = unsafe.objectFieldOffset(f_obj_field);
            f_long_off = unsafe.objectFieldOffset(f_long_field);
        } catch (Exception e) {
            System.out.println("reflection failed " + e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        final TestUnsafeVolatileGAS t = new TestUnsafeVolatileGAS();
        for (int i = 0; i < 100_000; i++) {
            if (t.testInt(i) != i-1) {
                throw new RuntimeException("bad result!");
            }
        }
        for (int i = 0; i < 100_000; i++) {
            if (t.testLong(i) != i-1) {
                throw new RuntimeException("bad result!");
            }
        }
        for (int i = 0; i < 100_000; i++) {
            if ((Integer)t.testObj(Integer.valueOf(i)) != i-1) {
                throw new RuntimeException("bad result!");
            }
        }
    }

    public int testInt(int i)
    {
        return unsafe.getAndSetInt(this, f_int_off, i);
    }

    public Object testObj(Object o)
    {
        return unsafe.getAndSetReference(this, f_obj_off, o);
    }
    public long testLong(long i)
    {
        return unsafe.getAndSetLong(this, f_long_off, i);
    }
}
