/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Test writeable VM Options with ranges.
 * @requires vm.flagless
 * @library /test/lib /runtime/CommandLine/OptionsValidation/common
 * @modules java.base/jdk.internal.misc
 *          jdk.attach/sun.tools.attach
 *          java.management
 * @run main/othervm -XX:MinHeapFreeRatio=0 -XX:MaxHeapFreeRatio=100 -Djdk.attach.allowAttachSelf TestOptionsWithRangesDynamic
 */

import java.util.List;
import jdk.test.lib.Asserts;
import optionsvalidation.JVMOption;
import optionsvalidation.JVMOptionsUtils;

public class TestOptionsWithRangesDynamic {

    private static List<JVMOption> allWriteableOptions;

    private static void excludeTestRange(String optionName) {
        for (JVMOption option: allWriteableOptions) {
            if (option.getName().equals(optionName)) {
                option.excludeTestMinRange();
                option.excludeTestMaxRange();
                break;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        int failedTests;

        /* Get only writeable options */
        allWriteableOptions = JVMOptionsUtils.getOptionsWithRange(origin -> (origin.contains("manageable") || origin.contains("rw")));

        /*
         * Exclude SoftMaxHeapSize as its valid range is only known at runtime.
         */
        excludeTestRange("SoftMaxHeapSize");

        Asserts.assertGT(allWriteableOptions.size(), 0, "Options with ranges not found!");

        System.out.println("Test " + allWriteableOptions.size() + " writeable options with ranges. Start test!");

        failedTests = JVMOptionsUtils.runDynamicTests(allWriteableOptions);

        failedTests += JVMOptionsUtils.runJcmdTests(allWriteableOptions);

        failedTests += JVMOptionsUtils.runAttachTests(allWriteableOptions);

        Asserts.assertEQ(failedTests, 0,
                String.format("%d tests failed! %s", failedTests, JVMOptionsUtils.getMessageWithFailures()));
    }
}
