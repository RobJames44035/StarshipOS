/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test SizeTTest
 * @bug 8054823
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management/sun.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm/timeout=600 -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:+UnlockExperimentalVMOptions SizeTTest
 * @summary testing of WB::set/getSizeTVMFlag()
 */
import jdk.test.lib.Platform;

public class SizeTTest {
    private static final String FLAG_NAME = "LargePageSizeInBytes";
    private static final Long[] TESTS = {0L, 100L, (long) Integer.MAX_VALUE,
        (1L << 32L) - 1L, 1L << 32L};
    private static final Long[] EXPECTED_64 = TESTS;
    private static final Long[] EXPECTED_32 = {0L, 100L,
        (long) Integer.MAX_VALUE, (1L << 32L) - 1L, 0L};

    public static void main(String[] args) throws Exception {
        VmFlagTest.runTest(FLAG_NAME, TESTS,
            Platform.is64bit() ? EXPECTED_64 : EXPECTED_32,
            VmFlagTest.WHITE_BOX::setSizeTVMFlag,
            VmFlagTest.WHITE_BOX::getSizeTVMFlag);
    }
}
