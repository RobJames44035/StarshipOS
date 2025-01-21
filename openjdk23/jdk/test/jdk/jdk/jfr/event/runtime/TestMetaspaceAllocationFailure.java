/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/**
 * @test
 * @summary Test jdk.MetaspaceAllocationFailure event
 * @requires vm.hasJFR
 * @library /test/lib
 * @modules java.base/jdk.internal.misc java.compiler
 * @run main/othervm -Xmx1G -XX:MaxMetaspaceSize=200M
 *      -XX:StartFlightRecording -Xshare:off
 *      -Xlog:gc+metaspace*=debug
 *      jdk.jfr.event.runtime.TestMetaspaceAllocationFailure
 * @run main/othervm -Xmx1G -XX:CompressedClassSpaceSize=10M
 *      -XX:StartFlightRecording -Xshare:off
 *      -Xlog:gc+metaspace*=debug
 *      jdk.jfr.event.runtime.TestMetaspaceAllocationFailure
 */
package jdk.jfr.event.runtime;

import java.util.concurrent.atomic.AtomicBoolean;
import jdk.jfr.consumer.RecordingStream;
import jdk.test.lib.classloader.GeneratingCompilingClassLoader;
import jdk.test.lib.Asserts;
import jtreg.SkippedException;

public class TestMetaspaceAllocationFailure {
    private static final int MAX_ITERATIONS = 100;

    public static void main(String... args) throws Exception {
        AtomicBoolean eventArrived = new AtomicBoolean(false);

        try (RecordingStream r = new RecordingStream()) {
            r.onEvent("jdk.MetaspaceAllocationFailure", e -> eventArrived.set(true));
            r.startAsync();

            try {
                int iteration = 0;
                while (!eventArrived.get()) {
                    GeneratingCompilingClassLoader cl = new GeneratingCompilingClassLoader();
                    cl.getGeneratedClasses(50, 20);
                    System.out.println("Iteration:" + iteration++);
                    if (iteration > MAX_ITERATIONS) {
                        throw new SkippedException("Exceeded MAX_ITERATIONS of " + MAX_ITERATIONS);
                    }
                }
                System.gc();
                System.out.println("main(): Event arrived");
            } catch (OutOfMemoryError e) {
                System.gc();
                System.out.println("main(): OutOfMemoryError (expected): " + e.getMessage());
            }

            Asserts.assertTrue(eventArrived.get());
        }
    }
}
