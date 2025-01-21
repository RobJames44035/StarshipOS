/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package gc.g1;

/**
 * @test TestShrinkAuxiliaryDataRunner
 * @key randomness
 * @bug 8038423 8061715
 * @summary Checks that decommitment occurs for JVM with different ObjectAlignmentInBytes values
 * @requires vm.gc.G1
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/timeout=720 gc.g1.TestShrinkAuxiliaryDataRunner
 */
public class TestShrinkAuxiliaryDataRunner {

    public static void main(String[] args) throws Exception {
        new TestShrinkAuxiliaryData().test();
    }
}
