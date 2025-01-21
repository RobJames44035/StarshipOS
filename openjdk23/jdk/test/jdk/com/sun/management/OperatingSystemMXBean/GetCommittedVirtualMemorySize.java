/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug     4858522 6191542
 * @summary Basic unit test of OperatingSystemMXBean.getCommittedVirtualMemorySize()
 * @requires vm.gc != "Z"
 * @author  Steve Bohne
 */

/*
 * This test is just a sanity check and does not check for the correct
 * value.  The correct value should be checked manually:
 * Solaris:
 *   1. In a shell, enter the command: "ps -efly"
 *   2. Find your process, and look in the "SZ" column.  Reported in Kbytes.
 * Linux:
 *   1. In a shell, enter the command: "ps -efly"
 *   2. Find your process, and look in the "SZ" column.  Reported in Kbytes.
 * Windows NT/XP/2000:
 *   1. Hit Ctrl-Alt-Delete, select Task Manager, go to Processes tab.
 *   2. Find your process and look in the "Mem Usage" column.  Reported in
 *      Kbytes.
 * Windows 98/ME:
 *   Not supported.
 */

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.*;

public class GetCommittedVirtualMemorySize {

    private static OperatingSystemMXBean mbean =
        (com.sun.management.OperatingSystemMXBean)
        ManagementFactory.getOperatingSystemMXBean();

    // Careful with these values.
    private static final long MIN_SIZE_FOR_PASS = 1;
    private static long       MAX_SIZE_FOR_PASS = Long.MAX_VALUE;

    private static boolean trace = false;

    public static void main(String args[]) throws Exception {
        if (args.length > 0 && args[0].equals("trace")) {
            trace = true;
        }

        long size = mbean.getCommittedVirtualMemorySize();
        if (size == -1) {
            System.out.println("getCommittedVirtualMemorySize() is not supported");
            return;
        }

        if (trace) {
            System.out.println("Committed virtual memory size in bytes: " +
                               size);
        }

        if (size < MIN_SIZE_FOR_PASS || size > MAX_SIZE_FOR_PASS) {
            throw new RuntimeException("Committed virtual memory size " +
                                       "illegal value: " + size + " bytes " +
                                       "(MIN = " + MIN_SIZE_FOR_PASS + "; " +
                                       "MAX = " + MAX_SIZE_FOR_PASS + ")");
        }

        System.out.println("Test passed.");
    }
}
