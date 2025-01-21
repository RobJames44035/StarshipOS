/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.jvm;

import static jdk.test.lib.Asserts.assertGreaterThan;

import jdk.jfr.internal.JVM;
import jdk.jfr.internal.JVMSupport;

/**
 * @test TestCounterTime
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @modules jdk.jfr/jdk.jfr.internal
 * @run main/othervm jdk.jfr.jvm.TestCounterTime
 */
public class TestCounterTime {

    public static void main(String... args) throws InterruptedException {
        // Not enabled
        assertCounterTime();

        JVMSupport.createJFR();
        assertCounterTime();
        // Enabled
        JVMSupport.destroyJFR();
    }

    private static void assertCounterTime() throws InterruptedException {
        long time1 = JVM.counterTime();
        assertGreaterThan(time1, 0L, "Counter time can't be negative.");

        Thread.sleep(1);

        long time2 = JVM.counterTime();
        assertGreaterThan(time2, time1, "Counter time must be increasing.");
    }
}
