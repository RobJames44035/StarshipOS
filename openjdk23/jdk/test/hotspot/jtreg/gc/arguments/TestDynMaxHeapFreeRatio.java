/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package gc.arguments;

import static jdk.test.lib.Asserts.assertEQ;
import static jdk.test.lib.Asserts.assertFalse;
import static jdk.test.lib.Asserts.assertTrue;
import jdk.test.lib.management.DynamicVMOption;

/**
 * @test TestDynMaxHeapFreeRatio
 * @bug 8028391
 * @summary Verify that MaxHeapFreeRatio flag is manageable
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 * @modules java.management
 * @run main gc.arguments.TestDynMaxHeapFreeRatio
 * @run main/othervm -XX:MinHeapFreeRatio=0 -XX:MaxHeapFreeRatio=100 gc.arguments.TestDynMaxHeapFreeRatio
 * @run main/othervm -XX:MinHeapFreeRatio=10 -XX:MaxHeapFreeRatio=50 -XX:-UseAdaptiveSizePolicy gc.arguments.TestDynMaxHeapFreeRatio
 * @run main/othervm -XX:MinHeapFreeRatio=10 -XX:MaxHeapFreeRatio=50 gc.arguments.TestDynMaxHeapFreeRatio
 * @run main/othervm -XX:MinHeapFreeRatio=51 -XX:MaxHeapFreeRatio=52 gc.arguments.TestDynMaxHeapFreeRatio
 * @run main/othervm -XX:MinHeapFreeRatio=75 -XX:MaxHeapFreeRatio=100 gc.arguments.TestDynMaxHeapFreeRatio
 */
public class TestDynMaxHeapFreeRatio {

    public static void main(String args[]) throws Exception {

        // low boundary value
        int minValue = DynamicVMOption.getInt("MinHeapFreeRatio");
        System.out.println("MinHeapFreeRatio= " + minValue);

        String badValues[] = {
            null,
            "",
            "not a number",
            "8.5", "-0.01",
            Integer.toString(Integer.MIN_VALUE),
            Integer.toString(Integer.MAX_VALUE),
            Integer.toString(minValue - 1),
            "-1024", "-1", "101", "1997"
        };

        String goodValues[] = {
            Integer.toString(minValue),
            Integer.toString(minValue + 1),
            Integer.toString((minValue + 100) / 2),
            "99", "100"
        };

        DynamicVMOption option = new DynamicVMOption("MaxHeapFreeRatio");

        assertTrue(option.isWriteable(), "Option " + option.name
                + " is expected to be writable");

        for (String v : badValues) {
            assertFalse(option.isValidValue(v),
                    "'" + v + "' is expected to be illegal for flag " + option.name);
        }
        for (String v : goodValues) {
            option.setValue(v);
            String newValue = option.getValue();
            assertEQ(v, newValue);
        }
    }
}
