/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.gc.objectcount;
import jdk.test.lib.jfr.GCHelper;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @requires vm.gc == "Serial" | vm.gc == null
 * @library /test/lib /test/jdk
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:-UseFastUnorderedTimeStamps -XX:+UseSerialGC -XX:MarkSweepDeadRatio=0 -XX:-UseCompressedOops -XX:-UseCompressedClassPointers -XX:+IgnoreUnrecognizedVMOptions jdk.jfr.event.gc.objectcount.TestObjectCountAfterGCEventWithSerial
 */
public class TestObjectCountAfterGCEventWithSerial {
    public static void main(String[] args) throws Exception {
        ObjectCountAfterGCEvent.test(GCHelper.gcSerialOld);
    }
}
