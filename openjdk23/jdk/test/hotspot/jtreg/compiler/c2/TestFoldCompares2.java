/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/**
 * @test
 * @bug 8286104
 * @summary Test Fold-compares are safe when C2 optimizes unstable_if traps
 *          (-XX:+OptimizeUnstableIf)
 *
 * @run main/othervm -XX:CompileCommand=compileOnly,java.lang.Short::valueOf
 *                   -XX:CompileCommand=compileonly,compiler.c2.TestFoldCompares2$Numbers::isSupported
 *                   -Xbatch compiler.c2.TestFoldCompares2
 */

package compiler.c2;

public class TestFoldCompares2 {
    public static Short value = Short.valueOf((short) 0);
    static void testShort() {
        // trigger compilation and bias to a cached value.
        for (int i=0; i<20_000; ++i) {
            value = Short.valueOf((short) 0);
        }

        // trigger deoptimization on purpose
        // the size of ShortCache.cache is hard-coded in java.lang.Short
        Short x = Short.valueOf((short) 128);
        if (x != 128) {
            throw new RuntimeException("wrong result!");
        }
    }

    static enum Numbers {
        One,
        Two,
        Three,
        Four,
        Five;

        boolean isSupported() {
            // ordinal() is inlined and leaves a copy region node, which blocks
            // fold-compares in the 1st iterGVN.
            return ordinal() >= Two.ordinal() && ordinal() <= Four.ordinal();
        }
    }

    static void testEnumValues() {
        Numbers local = Numbers.Two;

        for (int i = 0; i < 2_000_000; ++i) {
            local.isSupported();
        }
        // deoptimize
        Numbers.Five.isSupported();
    }

    public static void main(String[] args) {
        testShort();
        testEnumValues();
        System.out.println("Test passed.");
    }
}
