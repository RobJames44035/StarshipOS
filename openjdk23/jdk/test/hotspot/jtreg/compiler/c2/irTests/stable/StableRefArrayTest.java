/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @bug 8333791
 * @requires os.arch=="aarch64" | os.arch=="riscv64" | os.arch=="x86_64" | os.arch=="amd64"
 * @requires vm.gc.Parallel
 * @requires vm.compiler2.enabled
 * @summary Check stable field folding and barriers
 * @modules java.base/jdk.internal.vm.annotation
 * @library /test/lib /
 * @run driver compiler.c2.irTests.stable.StableRefArrayTest
 */

package compiler.c2.irTests.stable;

import compiler.lib.ir_framework.*;
import jdk.test.lib.Asserts;

import jdk.internal.vm.annotation.Stable;

public class StableRefArrayTest {

    public static void main(String[] args) {
        TestFramework tf = new TestFramework();
        tf.addTestClassesToBootClassPath();
        tf.addFlags(
            "-XX:+UnlockExperimentalVMOptions",
            "-XX:CompileThreshold=100",
            "-XX:-TieredCompilation",
            "-XX:+UseParallelGC"
        );
        tf.start();
    }

    static final Integer[] EMPTY_INTEGER = new Integer[] { null };
    static final Integer[] FULL_INTEGER = new Integer[] { 42 };

    static class Carrier {
        @Stable
        Integer[] field;

        @ForceInline
        public Carrier(int initLevel) {
            switch (initLevel) {
                case 0:
                    // Do nothing.
                    break;
                case 1:
                    field = EMPTY_INTEGER;
                    break;
                case 2:
                    field = FULL_INTEGER;
                    break;
                default:
                    throw new IllegalStateException("Unknown level");
            }
        }

        @ForceInline
        public void initEmpty() {
            field = EMPTY_INTEGER;
        }

        @ForceInline
        public void initFull() {
            field = FULL_INTEGER;
        }

    }

    static final Carrier BLANK_CARRIER = new Carrier(0);
    static final Carrier INIT_EMPTY_CARRIER = new Carrier(1);
    static final Carrier INIT_FULL_CARRIER = new Carrier(2);

    @Test
    @IR(counts = { IRNode.LOAD, ">0" })
    @IR(failOn = { IRNode.MEMBAR })
    static int testNoFold() {
        // Access should not be folded.
        // No barriers expected for plain fields.
        Integer[] is = BLANK_CARRIER.field;
        if (is != null) {
            Integer i = is[0];
            if (i != null) {
                return i;
            }
        }
        return 0;
    }

    @Test
    @IR(counts = { IRNode.LOAD, ">0" })
    @IR(failOn = { IRNode.MEMBAR })
    static int testPartialFold() {
        // Access should not be folded.
        // No barriers expected for plain fields.
        Integer[] is = INIT_EMPTY_CARRIER.field;
        if (is != null) {
            Integer i = is[0];
            if (i != null) {
                return i;
            }
        }
        return 0;
    }


    @Test
    @IR(failOn = { IRNode.LOAD, IRNode.MEMBAR })
    static int testFold() {
        // Access should be completely folded.
        Integer[] is = INIT_FULL_CARRIER.field;
        if (is != null) {
            Integer i = is[0];
            if (i != null) {
                return i;
            }
        }
        return 0;
    }

    @Test
    @IR(counts = { IRNode.MEMBAR_STORESTORE, "1" })
    static Carrier testConstructorBlankInit() {
        // Only the header barrier.
        return new Carrier(0);
    }

    @Test
    @IR(counts = { IRNode.MEMBAR_STORESTORE, "1" })
    static Carrier testConstructorEmptyInit() {
        // Only the header barrier.
        return new Carrier(1);
    }

    @Test
    @IR(counts = { IRNode.MEMBAR_STORESTORE, "1" })
    static Carrier testConstructorFullInit() {
        // Only the header barrier.
        return new Carrier(2);
    }

    @Test
    @IR(failOn = { IRNode.MEMBAR })
    static void testMethodEmptyInit() {
        // Reference inits do not have membars.
        INIT_EMPTY_CARRIER.initEmpty();
    }

    @Test
    @IR(failOn = { IRNode.MEMBAR })
    static void testMethodFullInit() {
        // Reference inits do not have membars.
        INIT_FULL_CARRIER.initFull();
    }

}
