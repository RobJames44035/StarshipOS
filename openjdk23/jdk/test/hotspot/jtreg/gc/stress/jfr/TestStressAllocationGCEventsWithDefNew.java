/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package jdk.jfr.event.gc.detailed;

/**
 * @test
 * @key randomness
 * @requires vm.hasJFR
 * @requires vm.gc == "null" | vm.gc == "Serial"
 * @library /test/lib /test/jdk
 * @run main/othervm -XX:+UseSerialGC -Xmx64m jdk.jfr.event.gc.detailed.TestStressAllocationGCEventsWithDefNew
 */
public class TestStressAllocationGCEventsWithDefNew {

    public static void main(String[] args) throws Exception {
        new StressAllocationGCEvents().run(args);
    }
}
