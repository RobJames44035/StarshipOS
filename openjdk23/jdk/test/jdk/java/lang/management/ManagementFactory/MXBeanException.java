/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug     5058319
 * @summary Check if a RuntimeException is wrapped by RuntimeMBeanException
 *          only once.
 *
 * @requires vm.gc != "Z" & vm.gc != "Shenandoah"
 * @author  Mandy Chung
 *
 * @build MXBeanException
 * @run main MXBeanException
 */

import java.lang.management.*;
import javax.management.*;
import java.util.*;
import static java.lang.management.ManagementFactory.*;

public class MXBeanException {
    private static MemoryPoolMXBean pool;

    public static void main(String[] argv) throws Exception {
        List<MemoryPoolMXBean> pools =
            ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean p : pools) {
            if (!p.isUsageThresholdSupported()) {
                pool = p;
                break;
            }
        }

        // check if UnsupportedOperationException is thrown
        try {
            pool.setUsageThreshold(1000);
            throw new RuntimeException("TEST FAILED: " +
                "UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }

        // check if UnsupportedOperationException is thrown
        // when calling through MBeanServer
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName on = new ObjectName(MEMORY_POOL_MXBEAN_DOMAIN_TYPE +
                                       ",name=" + pool.getName());
        Attribute att = new Attribute("UsageThreshold", 1000);
        try {
            mbs.setAttribute(on, att);
        } catch (RuntimeMBeanException e) {
            checkMBeanException(e);
        } catch (RuntimeOperationsException e) {
            checkMBeanException(e);
        }

        // check if UnsupportedOperationException is thrown
        // when calling through proxy

        MemoryPoolMXBean proxy = newPlatformMXBeanProxy(mbs,
                                     on.toString(),
                                     MemoryPoolMXBean.class);
        try {
            proxy.setUsageThreshold(1000);
            throw new RuntimeException("TEST FAILED: " +
                "UnsupportedOperationException not thrown via proxy");
        } catch (UnsupportedOperationException e) {
            // expected
        }

        System.out.println("Test passed");
    }

    private static void checkMBeanException(JMRuntimeException e) {
        Throwable cause = e.getCause();
        if (!(cause instanceof UnsupportedOperationException)) {
            throw new RuntimeException("TEST FAILED: " + cause +
                "is not UnsupportedOperationException");
        }
    }
}
