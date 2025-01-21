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
 * @run driver compiler.c2.irTests.stable.StableRefPlainTest
 */

package compiler.c2.irTests.stable;

import compiler.lib.ir_framework.*;
import jdk.test.lib.Asserts;

import jdk.internal.vm.annotation.Stable;

public class StableRefPlainTest {

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

    static final Integer INTEGER = 42;

    static class Carrier {
        @Stable
        Integer field;

        @ForceInline
        public Carrier(boolean init) {
            if (init) {
                field = INTEGER;
            }
        }

        @ForceInline
        public void init() {
            field = INTEGER;
        }
    }

    static final Carrier BLANK_CARRIER = new Carrier(false);
    static final Carrier INIT_CARRIER = new Carrier(true);

    @Test
    @IR(counts = { IRNode.LOAD, ">0" })
    @IR(failOn = { IRNode.MEMBAR })
    static int testNoFold() {
        // Access should not be folded.
        // No barriers expected for plain fields.
        Integer i = BLANK_CARRIER.field;
        return i != null ? i : 0;
    }

    @Test
    @IR(failOn = { IRNode.LOAD, IRNode.MEMBAR })
    static int testFold() {
        // Access should be completely folded.
        Integer i = INIT_CARRIER.field;
        return i != null ? i : 0;
    }

    @Test
    @IR(counts = { IRNode.MEMBAR_STORESTORE, "1" })
    static Carrier testConstructorBlankInit() {
        // Only the header barrier.
        return new Carrier(false);
    }

    @Test
    @IR(counts = { IRNode.MEMBAR_STORESTORE, "1" })
    static Carrier testConstructorFullInit() {
        // Only the header barrier.
        return new Carrier(true);
    }

    @Test
    @IR(failOn = { IRNode.MEMBAR })
    static void testMethodInit() {
        // Reference inits do not have membars.
        INIT_CARRIER.init();
    }

}
