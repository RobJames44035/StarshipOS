/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6305746
 * @key randomness
 * @summary Test that the null values returned by the ThreadMXBean work.
 * @author Eamonn McManus
 *
 * @run clean ThreadMXBeanTest
 * @run build ThreadMXBeanTest
 * @run main ThreadMXBeanTest
 */

import java.lang.management.*;
import java.util.*;
import javax.management.*;

public class ThreadMXBeanTest {
    public static void main(String[] args) throws Exception {
        MBeanServer mbs = MBeanServerFactory.newMBeanServer();
        ThreadMXBean tmb = ManagementFactory.getThreadMXBean();
        StandardMBean smb = new StandardMBean(tmb, ThreadMXBean.class, true);
        ObjectName on = new ObjectName("a:type=ThreadMXBean");
        mbs.registerMBean(smb, on);
        ThreadMXBean proxy = JMX.newMXBeanProxy(mbs, on, ThreadMXBean.class);
        long[] ids1 = proxy.getAllThreadIds();

        // Add some random ids to the list so we'll get back null ThreadInfo
        long[] ids2 = new long[ids1.length + 10];
        System.arraycopy(ids1, 0, ids2, 0, ids1.length);
        Random r = new Random();
        for (int i = ids1.length; i < ids2.length; i++)
            ids2[i] = Math.abs(r.nextLong());
        // Following line produces an exception if null values not handled
        ThreadInfo[] info = proxy.getThreadInfo(ids2);
        boolean sawNull = false;
        for (ThreadInfo ti : info) {
            if (ti == null)
                sawNull = true;
        }
        if (!sawNull)
            throw new Exception("No null value in returned array");
        System.out.println("TEST PASSED");
    }
}
