/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test TestStableUShort
 * @summary tests on stable fields and arrays
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 * @modules java.base/jdk.internal.vm.annotation
 * @build jdk.test.whitebox.WhiteBox
 *
 * @run main/bootclasspath/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:+AlwaysIncrementalInline
 *                                 -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xcomp
 *                                 -XX:-TieredCompilation
 *                                 -XX:+FoldStableValues
 *                                 -XX:CompileOnly=*TestStableUShort*::get*
 *                                 compiler.stable.TestStableUShort
 * @run main/bootclasspath/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:+AlwaysIncrementalInline
 *                                 -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xcomp
 *                                 -XX:-TieredCompilation
 *                                 -XX:-FoldStableValues
 *                                 -XX:CompileOnly=*TestStableUShort*::get*
 *                                 compiler.stable.TestStableUShort
 *
 * @run main/bootclasspath/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:+AlwaysIncrementalInline
 *                                 -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xcomp
 *                                 -XX:+TieredCompilation -XX:TieredStopAtLevel=1
 *                                 -XX:+FoldStableValues
 *                                 -XX:CompileOnly=*TestStableUShort*::get*
 *                                 compiler.stable.TestStableUShort
 * @run main/bootclasspath/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:+AlwaysIncrementalInline
 *                                 -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xcomp
 *                                 -XX:+TieredCompilation -XX:TieredStopAtLevel=1
 *                                 -XX:-FoldStableValues
 *                                 -XX:CompileOnly=*TestStableUShort*::get*
 *                                 compiler.stable.TestStableUShort
 */

package compiler.stable;

import jdk.internal.vm.annotation.Stable;

import java.lang.reflect.InvocationTargetException;

public class TestStableUShort {
    static final boolean isStableEnabled = StableConfiguration.isStableEnabled;

    public static void main(String[] args) throws Exception {
        run(UShortStable.class);
        run(UShortArrayDim1.class);

        if (failed) {
            throw new Error("TEST FAILED");
        }
    }

    /* ==================================================== */

    static class UShortStable {
        public @Stable short v;

        public static final UShortStable c = new UShortStable();

        public static int get() { return c.v & 0xFFFF; }

        public static void test() throws Exception {
            short v1 = -1, v2 = 1;

            c.v = v1; int r1 = get();
            c.v = v2; int r2 = get();

            assertEquals(r1, v1 & 0xFFFF);
            assertEquals(r2, (isStableEnabled ? v1 : v2) & 0xFFFF);
        }
    }

    /* ==================================================== */

    static class UShortArrayDim1 {
        public @Stable short[] v;

        public static final UShortArrayDim1 c = new UShortArrayDim1();

        public static short[] get()  { return c.v; }
        public static int    get1() { return get()[0] & 0xFFFF; }

        public static void test() throws Exception {
            short v1 = -1, v2 = 1;

            c.v = new short[1];
            c.v[0] = v1; int r1 = get1();
            c.v[0] = v2; int r2 = get1();

            assertEquals(r1, v1 & 0xFFFF);
            assertEquals(r2, (isStableEnabled ? v1 : v2) & 0xFFFF);
        }
    }

    /* ==================================================== */
    // Auxiliary methods
    static void assertEquals(int i, int j) { if (i != j)  throw new AssertionError(i + " != " + j); }
    static void assertTrue(boolean b) { if (!b)  throw new AssertionError(); }

    static boolean failed = false;

    public static void run(Class<?> test) {
        Throwable ex = null;
        System.out.print(test.getName()+": ");
        try {
            test.getMethod("test").invoke(null);
        } catch (InvocationTargetException e) {
            ex = e.getCause();
        } catch (Throwable e) {
            ex = e;
        } finally {
            if (ex == null) {
                System.out.println("PASSED");
            } else {
                failed = true;
                System.out.println("FAILED");
                ex.printStackTrace(System.out);
            }
        }
    }
}
