/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug     4858522
 * @summary Basic unit test of HotspotClassLoadingMBean.getLoadedClassSize()
 * @author  Steve Bohne
 *
 * @run main/othervm -XX:+UsePerfData GetLoadedClassSize
 */

/*
 * This test is just a sanity check and does not check for the correct value.
 */

import sun.management.*;

public class GetLoadedClassSize {

    private static HotspotClassLoadingMBean mbean =
        (HotspotClassLoadingMBean)ManagementFactoryHelper.getHotspotClassLoadingMBean();

    // Careful with these values.
    private static final long MIN_VALUE_FOR_PASS = 1;
    private static final long MAX_VALUE_FOR_PASS = Long.MAX_VALUE;

    private static boolean trace = false;

    public static void main(String args[]) throws Exception {
        if (args.length > 0 && args[0].equals("trace")) {
            trace = true;
        }

        long value = mbean.getLoadedClassSize();

        if (trace) {
            System.out.println("Loaded class size (bytes): " + value);
        }

        if (value < MIN_VALUE_FOR_PASS || value > MAX_VALUE_FOR_PASS) {
            throw new RuntimeException("Loaded class size " +
                                       "illegal value: " + value + " bytes " +
                                       "(MIN = " + MIN_VALUE_FOR_PASS + "; " +
                                       "MAX = " + MAX_VALUE_FOR_PASS + ")");
        }

        // increase the size
        Class.forName("ClassToLoad1");


        long value2 = mbean.getLoadedClassSize();

        if (trace) {
            System.out.println("Loaded class size2 (bytes): " + value2);
        }

        if (value2 <= value) {
            throw new RuntimeException("Loaded class size " +
                                       "did not increase " +
                                       "(value = " + value + "; " +
                                       "value2 = " + value2 + ")");
        }
        System.out.println("Test passed.");
    }
}

class ClassToLoad1 {}
