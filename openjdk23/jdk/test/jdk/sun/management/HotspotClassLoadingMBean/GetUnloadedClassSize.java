/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug     4858522
 * @summary Basic unit test of HotspotClassLoadingMBean.getUnloadedClassSize()
 * @author  Steve Bohne
 *
 * @run main/othervm -XX:+UsePerfData GetUnloadedClassSize
 */

/*
 * This test is just a sanity check and does not check for the correct value.
 */

import sun.management.*;

public class GetUnloadedClassSize {

    private static HotspotClassLoadingMBean mbean =
        (HotspotClassLoadingMBean)ManagementFactoryHelper.getHotspotClassLoadingMBean();

    // Careful with these values.
    private static final long MIN_VALUE_FOR_PASS = 0;
    private static final long MAX_VALUE_FOR_PASS = Long.MAX_VALUE;

    private static boolean trace = false;

    public static void main(String args[]) throws Exception {
        if (args.length > 0 && args[0].equals("trace")) {
            trace = true;
        }

        long value = mbean.getUnloadedClassSize();

        if (trace) {
            System.out.println("Unloaded class size (bytes): " + value);
        }

        if (value < MIN_VALUE_FOR_PASS || value > MAX_VALUE_FOR_PASS) {
            throw new RuntimeException("Unloaded class size " +
                                       "illegal value: " + value + " bytes " +
                                       "(MIN = " + MIN_VALUE_FOR_PASS + "; " +
                                       "MAX = " + MAX_VALUE_FOR_PASS + ")");
        }

        System.out.println("Test passed.");
    }
}
