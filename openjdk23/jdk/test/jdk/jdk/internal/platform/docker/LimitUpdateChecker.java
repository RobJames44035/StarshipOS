/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.io.File;
import java.io.FileOutputStream;
import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import jdk.internal.platform.Metrics;


// Check dynamic limits updating. Metrics (java) side.
public class LimitUpdateChecker {

    private static final File UPDATE_FILE = new File("/tmp", "limitsUpdated");
    private static final File STARTED_FILE = new File("/tmp", "started");

    public static void main(String[] args) throws Exception {
        System.out.println("Running LimitUpdateChecker...");
        Metrics metrics = jdk.internal.platform.Container.metrics();
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        printMetrics(osBean, metrics); // initial limits
        createStartedFile();
        while (!UPDATE_FILE.exists()) {
            Thread.sleep(200);
        }
        System.out.println("'limitsUpdated' file appeared. Stopped loop.");
        printMetrics(osBean, metrics); // updated limits
        System.out.println("LimitUpdateChecker DONE.");
    }

    private static void printMetrics(OperatingSystemMXBean osBean, Metrics metrics) {
        System.out.println(String.format("Runtime.availableProcessors: %d", Runtime.getRuntime().availableProcessors()));
        System.out.println(String.format("OperatingSystemMXBean.getAvailableProcessors: %d", osBean.getAvailableProcessors()));
        System.out.println("Metrics.getMemoryLimit() == " + metrics.getMemoryLimit());
        System.out.println(String.format("OperatingSystemMXBean.getTotalMemorySize: %d", osBean.getTotalMemorySize()));
    }

    private static void createStartedFile() throws Exception {
        FileOutputStream fout = new FileOutputStream(STARTED_FILE);
        fout.close();
    }

}
