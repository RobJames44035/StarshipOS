/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8311130
 * @summary Test synchronization between SVE arguments and CPU features
 *
 * @requires os.arch == "aarch64" & vm.compiler2.enabled
 * @library /test/lib /
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller
 *             jdk.test.whitebox.WhiteBox
 *
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -XX:UseSVE=0
 *                   compiler.arguments.TestSyncCPUFeaturesWithSVEFlags
 *
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -XX:UseSVE=1
 *                   compiler.arguments.TestSyncCPUFeaturesWithSVEFlags
 *
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -XX:UseSVE=2
 *                   compiler.arguments.TestSyncCPUFeaturesWithSVEFlags
 *
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -XX:MaxVectorSize=8
 *                   compiler.arguments.TestSyncCPUFeaturesWithSVEFlags
 */

package compiler.arguments;

import java.util.List;
import java.util.Arrays;

import jdk.test.lib.Asserts;
import jdk.test.whitebox.WhiteBox;

public class TestSyncCPUFeaturesWithSVEFlags {

    private static final WhiteBox WB = WhiteBox.getWhiteBox();

    public static void main(String[] args) {
        int sve_level = WB.getUintVMFlag("UseSVE").intValue();
        List<String> features = Arrays.asList(WB.getCPUFeatures().split(", "));
        boolean has_sve = features.contains("sve");
        boolean has_sve2 = features.contains("sve2");
        switch (sve_level) {
            case 0: {
                // No sve and sve2
                Asserts.assertFalse(has_sve);
                Asserts.assertFalse(has_sve2);
                break;
            }
            case 1: {
                // Only has sve, no sve2
                Asserts.assertTrue(has_sve);
                Asserts.assertFalse(has_sve2);
                break;
            }
            case 2: {
                // Has both sve and sve2
                Asserts.assertTrue(has_sve);
                Asserts.assertTrue(has_sve2);
                break;
            }
            default: {
                // Should not reach here
                Asserts.assertTrue(false);
                break;
            }
        }
    }
}
