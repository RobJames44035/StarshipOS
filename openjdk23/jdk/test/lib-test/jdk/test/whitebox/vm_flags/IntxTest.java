/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test IntxTest
 * @bug 8038756
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 * @modules java.management/sun.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm/timeout=600 -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xint -XX:-ProfileInterpreter IntxTest
 * @summary testing of WB::set/getIntxVMFlag()
 * @author igor.ignatyev@oracle.com
 */
import jdk.test.lib.Platform;
public class IntxTest {
    private static final String FLAG_NAME = "OnStackReplacePercentage";
    private static final String FLAG_DEBUG_NAME = "BciProfileWidth";
    private static final long COMPILE_THRESHOLD = VmFlagTest.WHITE_BOX.getIntxVMFlag("CompileThreshold");
    private static final Long[] TESTS = {0L, 100L, (long)(Integer.MAX_VALUE>>3)*100L};

    public static void main(String[] args) throws Exception {
        find_and_set_max_osrp();
        VmFlagTest.runTest(FLAG_NAME, TESTS,
            VmFlagTest.WHITE_BOX::setIntxVMFlag,
            VmFlagTest.WHITE_BOX::getIntxVMFlag);
        VmFlagTest.runTest(FLAG_DEBUG_NAME, VmFlagTest.WHITE_BOX::getIntxVMFlag);
    }

    static void find_and_set_max_osrp() {
        long max_percentage_limit = (long)(Integer.MAX_VALUE>>3)*100L;
        max_percentage_limit = COMPILE_THRESHOLD == 0  ? max_percentage_limit : max_percentage_limit/COMPILE_THRESHOLD;
        if (Platform.is32bit() && max_percentage_limit > Integer.MAX_VALUE) {
          max_percentage_limit = Integer.MAX_VALUE;
        }
        TESTS[2] = max_percentage_limit;
    }
}

