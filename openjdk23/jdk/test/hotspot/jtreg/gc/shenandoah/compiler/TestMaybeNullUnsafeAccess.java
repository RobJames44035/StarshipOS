/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**
 * @test
 * @summary cast before unsafe access moved in dominating null check null path causes crash
 * @requires vm.gc.Shenandoah
 * @modules java.base/jdk.internal.misc:+open
 *
 * @run main/othervm -XX:-UseOnStackReplacement -XX:-BackgroundCompilation -XX:-TieredCompilation
 *                   TestMaybeNullUnsafeAccess
 *
 * @run main/othervm -XX:-UseOnStackReplacement -XX:-BackgroundCompilation -XX:-TieredCompilation
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC
 *                   TestMaybeNullUnsafeAccess
 *
 */

import jdk.internal.misc.Unsafe;

import java.lang.reflect.Field;

public class TestMaybeNullUnsafeAccess {

    static final jdk.internal.misc.Unsafe UNSAFE = Unsafe.getUnsafe();
    static final long F_OFFSET;

    static class A {
        int f;
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
        return (A) o;
    }

    static int test(Object o) {
        int f = 0;
        for (int i = 0; i < 100; i++) {
            A a = test_helper(o);
            f = UNSAFE.getInt(a, F_OFFSET);
        }
        return f;
    }

    static public void main(String[] args) {
        A a = new A();
        for (int i = 0; i < 20000; i++) {
            test_helper(null);
            test_helper(a);
            test(a);
        }
    }

}
