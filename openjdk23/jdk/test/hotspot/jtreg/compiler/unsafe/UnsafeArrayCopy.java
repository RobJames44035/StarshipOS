/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8316756
 * @summary Test UNSAFE.copyMemory in combination with Escape Analysis
 * @library /test/lib
 *
 * @modules java.base/jdk.internal.misc
 *
 * @run main/othervm -XX:-TieredCompilation -Xbatch -XX:CompileCommand=quiet -XX:CompileCommand=compileonly,compiler.unsafe.UnsafeArrayCopy::test*
 *                   compiler.unsafe.UnsafeArrayCopy
 */

package compiler.unsafe;

import java.lang.reflect.*;
import java.util.*;

import jdk.internal.misc.Unsafe;


public class UnsafeArrayCopy {

    private static Unsafe UNSAFE = Unsafe.getUnsafe();

    static long SRC_BASE = UNSAFE.allocateMemory(4);
    static long DST_BASE = UNSAFE.allocateMemory(4);

    static class MyClass {
        int x;
    }

    static int test() {
        MyClass obj = new MyClass(); // Non-escaping to trigger Escape Analysis
        UNSAFE.copyMemory(null, SRC_BASE, null, DST_BASE, 4);
        obj.x = 42;
        return obj.x;
    }

    static int[] test2() {
         int[] src = new int[4];
         int[] dst = new int[4];
         MyClass obj = new MyClass();
         UNSAFE.copyMemory(src, 0, dst, 0, 4);
         obj.x = 42;
         dst[1] = obj.x;
         return dst;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50_000; ++i) {
            test();
            test2();
        }
    }
}
