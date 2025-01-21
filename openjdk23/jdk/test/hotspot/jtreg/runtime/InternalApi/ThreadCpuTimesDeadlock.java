/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7196045
 * @bug 8014294
 * @summary Possible JVM deadlock in ThreadTimesClosure when using HotspotInternal non-public API.
 * @modules java.management/sun.management
 * @run main/othervm -XX:+UsePerfData -Xmx128m ThreadCpuTimesDeadlock
 */

/*
 * @test
 * @bug 8264649
 * @summary OSR compiled method crash when UseTLAB is off
 * @requires vm.debug
 * @modules java.management/sun.management
 * @run main/othervm -XX:-UseTLAB -XX:+UsePerfData -Xmx128m ThreadCpuTimesDeadlock
 */

import java.lang.management.ManagementFactory;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public class ThreadCpuTimesDeadlock {

    public static byte[] dummy;
    public static long duration = 10 * 1000;
    private static final String HOTSPOT_INTERNAL = "sun.management:type=HotspotInternal";

    public static void main(String[] args) {

        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName objName= null;
        try {
            ObjectName hotspotInternal = new ObjectName(HOTSPOT_INTERNAL);
            try {
                server.registerMBean(new sun.management.HotspotInternal(), hotspotInternal);
            } catch (JMException e) {
                throw new RuntimeException("HotSpotWatcher: Failed to register the HotspotInternal MBean" + e);
            }
            objName= new ObjectName("sun.management:type=HotspotThreading");

        } catch (MalformedObjectNameException e1) {
            throw new RuntimeException("Bad object name" + e1);
        }

        // Thread that allocs memory to generate GC's
        Thread allocThread = new Thread() {
          public void run() {
            while (true) {
              dummy = new byte[4096];
            }
          }
        };

        allocThread.setDaemon(true);
        allocThread.start();

        long endTime = System.currentTimeMillis() + duration;
        long i = 0;
        while (true) {
            try {
                server.getAttribute(objName, "InternalThreadCpuTimes");
            } catch (Exception ex) {
                System.err.println("Exception while getting attribute: " + ex);
            }
            i++;
            if (i % 10000 == 0) {
                System.out.println("Successful iterations: " + i);
            }
            if (System.currentTimeMillis() > endTime) {
                break;
            }
        }
        System.out.println("PASSED.");
    }
}
