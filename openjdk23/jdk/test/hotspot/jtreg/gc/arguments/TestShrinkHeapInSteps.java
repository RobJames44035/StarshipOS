/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package gc.arguments;

/*
 * @test TestShrinkHeapInSteps
 * @summary Verify that -XX:-ShrinkHeapInSteps works properly.
 * @requires vm.gc != "Z" & vm.gc != "Shenandoah"
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver/timeout=240 gc.arguments.TestShrinkHeapInSteps
 */

import java.util.LinkedList;
import java.util.Arrays;
import jdk.test.lib.Utils;

public class TestShrinkHeapInSteps {
    public static void main(String args[]) throws Exception {
        LinkedList<String> options = new LinkedList<>(
                Arrays.asList(Utils.getFilteredTestJavaOpts("-XX:[^ ]*HeapFreeRatio","-XX:\\+ExplicitGCInvokesConcurrent"))
        );

        // Leverage the existing TestMaxMinHeapFreeRatioFlags test, but pass
        // "false" for the shrinkHeapInSteps argument. This will cause it to
        // run with -XX:-ShrinkHeapInSteps, and only do 1 full GC instead of 10.
        TestMaxMinHeapFreeRatioFlags.positiveTest(10, false, 90, false, false, options);
        TestMaxMinHeapFreeRatioFlags.positiveTest(10, true, 80, false, false, options);
        TestMaxMinHeapFreeRatioFlags.positiveTest(20, false, 70, true, false, options);
        TestMaxMinHeapFreeRatioFlags.positiveTest(25, true, 65, true, false, options);
        TestMaxMinHeapFreeRatioFlags.positiveTest(40, false, 50, false, false, options);
    }
}
