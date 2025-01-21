/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug     4858522
 * @summary Basic unit test of UnixOperatingSystemMXBean.getOpenFileDescriptorCount()
 * @author  Steve Bohne
 * @requires os.family != "windows"
 *
 * @run main GetOpenFileDescriptorCount
 */

import com.sun.management.UnixOperatingSystemMXBean;
import java.lang.management.*;

public class GetOpenFileDescriptorCount {

    private static UnixOperatingSystemMXBean mbean =
        (UnixOperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    private static final long MIN_COUNT_FOR_PASS = 1;
    private static long       maxCountForPass = Long.MAX_VALUE;

    public static void main(String args[]) throws Exception {

        long maxCount = mbean.getMaxFileDescriptorCount();
        if (maxCount > 0) {
            maxCountForPass = maxCount;
        }

        long count = mbean.getOpenFileDescriptorCount();

        System.out.println("Open file descriptor count: " + count);

        if (count < MIN_COUNT_FOR_PASS || count > maxCountForPass) {
            throw new RuntimeException("Open file descriptor count " +
                                       "illegal value: " + count + " bytes " +
                                       "(MIN = " + MIN_COUNT_FOR_PASS + "; " +
                                       "MAX = " + maxCountForPass + ")");
        }

        System.out.println("Test passed.");
    }
}
