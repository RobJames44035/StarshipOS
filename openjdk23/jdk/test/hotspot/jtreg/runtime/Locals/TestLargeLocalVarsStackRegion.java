/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8265756
 * @library /test/lib /
 * @compile TestLargeLocalVarsStackRegionHelper.jasm
 * @run main runtime.Locals.TestLargeLocalVarsStackRegion
 */

package runtime.Locals;

import jdk.test.lib.Asserts;

public class TestLargeLocalVarsStackRegion {

    // Some platforms (such as windows-aarch64) may have
    // stack page touch order restrictions.
    // Test calls method with large local vars stack region
    // to trigger usage of several stack memory pages and
    // check the validity of the touch order.
    //
    // Helper method is written in jasm as this allows to
    // specify local vars stack region size directly.
    public static void main(String args[]) {
        Asserts.assertEQ(TestLargeLocalVarsStackRegionHelper.tst(), 0);
    }
}
