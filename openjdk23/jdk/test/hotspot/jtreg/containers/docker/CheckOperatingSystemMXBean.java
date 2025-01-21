/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import jdk.internal.platform.Metrics;

public class CheckOperatingSystemMXBean {

    public static void main(String[] args) {
        System.out.println("Checking OperatingSystemMXBean");
        Metrics metrics = jdk.internal.platform.Container.metrics();
        System.out.println("Metrics instance: " + (metrics == null ? "null" : "non-null"));
        if (metrics != null) {
            System.out.println("Metrics.getMemoryAndSwapLimit() == " + metrics.getMemoryAndSwapLimit());
            System.out.println("Metrics.getMemoryLimit() == " + metrics.getMemoryLimit());
            System.out.println("Metrics.getMemoryAndSwapUsage() == " + metrics.getMemoryAndSwapUsage());
            System.out.println("Metrics.getMemoryUsage() == " + metrics.getMemoryUsage());
        }

        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        System.out.println(String.format("Runtime.availableProcessors: %d", Runtime.getRuntime().availableProcessors()));
        System.out.println(String.format("OperatingSystemMXBean.getAvailableProcessors: %d", osBean.getAvailableProcessors()));
        System.out.println(String.format("OperatingSystemMXBean.getTotalMemorySize: %d", osBean.getTotalMemorySize()));
        System.out.println(String.format("OperatingSystemMXBean.getTotalPhysicalMemorySize: %d", osBean.getTotalPhysicalMemorySize()));
        System.out.println(String.format("OperatingSystemMXBean.getFreeMemorySize: %d", osBean.getFreeMemorySize()));
        System.out.println(String.format("OperatingSystemMXBean.getFreePhysicalMemorySize: %d", osBean.getFreePhysicalMemorySize()));
        System.out.println(String.format("OperatingSystemMXBean.getTotalSwapSpaceSize: %d", osBean.getTotalSwapSpaceSize()));
        System.out.println(String.format("OperatingSystemMXBean.getFreeSwapSpaceSize: %d", osBean.getFreeSwapSpaceSize()));
        System.out.println(String.format("OperatingSystemMXBean.getCpuLoad: %f", osBean.getCpuLoad()));
        System.out.println(String.format("OperatingSystemMXBean.getSystemCpuLoad: %f", osBean.getSystemCpuLoad()));
    }

}
