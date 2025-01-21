/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test UintxTest
 * @bug 8038756
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management/sun.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm/timeout=600 -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI UintxTest
 * @summary testing of WB::set/getUintxVMFlag()
 * @author igor.ignatyev@oracle.com
 */
import jdk.test.lib.Platform;

public class UintxTest {
    private static final String FLAG_NAME = "VerifyGCStartAt";
    private static final String FLAG_DEBUG_NAME = "CodeCacheMinimumUseSpace";
    private static final Long[] TESTS = {0L, 100L, (long) Integer.MAX_VALUE,
        (1L << 32L) - 1L, 1L << 32L};
    private static final Long[] EXPECTED_64 = TESTS;
    private static final Long[] EXPECTED_32 = {0L, 100L,
        (long) Integer.MAX_VALUE, (1L << 32L) - 1L, 0L};

    public static void main(String[] args) throws Exception {
        VmFlagTest.runTest(FLAG_NAME, TESTS,
            Platform.is64bit() ? EXPECTED_64 : EXPECTED_32,
            VmFlagTest.WHITE_BOX::setUintxVMFlag,
            VmFlagTest.WHITE_BOX::getUintxVMFlag);
        VmFlagTest.runTest(FLAG_DEBUG_NAME, VmFlagTest.WHITE_BOX::getUintxVMFlag);
    }
}

