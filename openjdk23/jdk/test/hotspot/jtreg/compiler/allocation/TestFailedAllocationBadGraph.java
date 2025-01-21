/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * bug 8279219
 * @summary C2 crash when allocating array of size too large
 * @requires vm.compiler2.enabled
 * @library /test/lib /
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -ea -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:-BackgroundCompilation TestFailedAllocationBadGraph
 */

import jdk.test.whitebox.WhiteBox;
import java.lang.reflect.Method;
import compiler.whitebox.CompilerWhiteBoxTest;

public class TestFailedAllocationBadGraph {
    private static final WhiteBox WHITE_BOX = WhiteBox.getWhiteBox();

    private static long[] array;
    private static int field;
    private static volatile int barrier;

    public static void main(String[] args) throws Exception {
        run("test1");
        run("test2");
    }

    private static void run(String method) throws Exception {
        Method m = TestFailedAllocationBadGraph.class.getDeclaredMethod(method);
        WHITE_BOX.enqueueMethodForCompilation(m, CompilerWhiteBoxTest.COMP_LEVEL_FULL_OPTIMIZATION);
        if (!WHITE_BOX.isMethodCompiled(m) || WHITE_BOX.getMethodCompilationLevel(m) != CompilerWhiteBoxTest.COMP_LEVEL_FULL_OPTIMIZATION) {
            throw new RuntimeException("should still be compiled");
        }
    }

    private static int test1() {
        int length = Integer.MAX_VALUE;
        try {
            array = new long[length];
        } catch (OutOfMemoryError outOfMemoryError) {
            barrier = 0x42;
            length = field;
        }
        return length;
    }

    private static int test2() {
        int length = -1;
        try {
            array = new long[length];
        } catch (OutOfMemoryError outOfMemoryError) {
            barrier = 0x42;
            length = field;
        }
        return length;
    }
}
