/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.lang.reflect.Field;

/**
 * @test
 * @bug 8175887
 * @summary C1 value numbering handling of Unsafe.get*Volatile is incorrect
 * @modules java.base/jdk.internal.misc
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:TieredStopAtLevel=1 UnsafeVolatileGuardTest
 */
public class UnsafeVolatileGuardTest {
    volatile static private int a;
    static private int b;

    static final jdk.internal.misc.Unsafe UNSAFE = jdk.internal.misc.Unsafe.getUnsafe();

    static final Object BASE;
    static final long OFFSET;

    static {
        try {
            Field f = UnsafeVolatileGuardTest.class.getDeclaredField("a");
            BASE = UNSAFE.staticFieldBase(f);
            OFFSET = UNSAFE.staticFieldOffset(f);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void test() {
        int tt = b; // makes the JVM CSE the value of b

        while (UNSAFE.getIntVolatile(BASE, OFFSET) == 0) {} // burn
        if (b == 0) {
            System.err.println("wrong value of b");
            System.exit(1); // fail hard to report the error
        }
    }

    public static void main(String [] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            new Thread(UnsafeVolatileGuardTest::test).start();
        }
        b = 1;
        a = 1;
    }
}
