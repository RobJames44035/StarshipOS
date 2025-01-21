/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package jdk.jfr.event.gc.detailed;

/**
 * @test
 * @key randomness
 * @requires vm.hasJFR
 * @requires vm.gc == "null" | vm.gc == "G1"
 * @library /test/lib /test/jdk
 * @run main/othervm -XX:+UseG1GC -Xmx64m jdk.jfr.event.gc.detailed.TestStressAllocationGCEventsWithG1
 */
public class TestStressAllocationGCEventsWithG1 {

    public static void main(String[] args) throws Exception {
        new StressAllocationGCEvents().run(args);
    }
}
