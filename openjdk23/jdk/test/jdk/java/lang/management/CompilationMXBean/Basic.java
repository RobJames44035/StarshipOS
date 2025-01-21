/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5011189 8004928
 * @summary Unit test for java.lang.management.CompilationMXBean
 *
 * @run main/othervm -Xcomp -Xbatch Basic
 */
import java.lang.management.*;

public class Basic {

    public static void main(String args[]) {
        CompilationMXBean mb = ManagementFactory.getCompilationMXBean();
        if (mb == null) {
            System.out.println("The virtual machine doesn't have a compilation system");
            return;
        }

        // Exercise getName() method
        System.out.println(mb.getName());

        // If compilation time monitoring isn't supported then we are done
        if (!mb.isCompilationTimeMonitoringSupported()) {
            System.out.println("Compilation time monitoring not supported.");
            return;
        }

        // Exercise getTotalCompilationTime();
        long time;

        // If the compiler has already done some work then we are done
        time = mb.getTotalCompilationTime();
        if (time > 0) {
            printCompilationTime(time);
            return;
        }

        // Now the hard bit - we do random work on the assumption
        // that the compiler will be used.

        System.out.println("Doing random work...");

        java.util.Locale.getAvailableLocales();
        java.security.Security.getProviders();
        java.nio.channels.spi.SelectorProvider.provider();

        time = mb.getTotalCompilationTime();
        if (time > 0) {
            printCompilationTime(time);
        } else {
            throw new RuntimeException("getTimeCompilionTime returns 0");
        }
    }

    static void printCompilationTime(long time) {
        System.out.println("Total compilation time: " + time + " ms");
    }
}
