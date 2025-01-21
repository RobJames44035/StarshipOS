/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**
 * @test
 * @bug 8176506
 * @summary cast before unsafe access moved in dominating null check null path causes crash
 * @modules java.base/jdk.internal.misc:+open
 *
 * @run main/othervm -Xbatch -XX:-UseOnStackReplacement TestMaybeNullUnsafeAccess
 *
 */

import jdk.internal.misc.Unsafe;
import java.lang.reflect.Field;

public class TestMaybeNullUnsafeAccess {

    static final jdk.internal.misc.Unsafe UNSAFE = Unsafe.getUnsafe();
    static final long F_OFFSET;

    static class A {
        int f;
        A(int f) {
            this.f = f;
        }
    }

    static {
        try {
            Field fField = A.class.getDeclaredField("f");
            F_OFFSET = UNSAFE.objectFieldOffset(fField);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static A test_helper(Object o) {
        // this includes a check for null with both branches taken
        return (A)o;
    }


    // Loop is unswitched because of the test for null from the
    // checkcast above, unsafe access is copied in each branch, the
    // compiler sees a memory access to a null object
    static int test1(Object o, long offset) {
        int f = 0;
        for (int i = 0; i < 100; i++) {
            A a = test_helper(o);
            f = UNSAFE.getInt(a, offset);
        }
        return f;
    }

    // Same as above except because we know the offset of the access
    // is small, we can deduce object a cannot be null
    static int test2(Object o) {
        int f = 0;
        for (int i = 0; i < 100; i++) {
            A a = test_helper(o);
            f = UNSAFE.getInt(a, F_OFFSET);
        }
        return f;
    }

    static public void main(String[] args) {
        A a = new A(0x42);
        for (int i = 0; i < 20000; i++) {
            test_helper(null);
            test_helper(a);
            test1(a, F_OFFSET);
            test2(a);
        }
    }

}
