/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.gc.detailed;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 *
 * @requires vm.gc == "Serial" | vm.gc == null
 * @library /test/lib /test/jdk
 * @run main jdk.jfr.event.gc.detailed.TestPromotionFailedEventWithDefNew
 */
public class TestPromotionFailedEventWithDefNew {
    public static void main(String[] args) throws Throwable {
        PromotionFailedEvent.test("TestPromotionFailedEventWithDefNew", new String[] {"-XX:+UnlockExperimentalVMOptions",
            "-XX:-UseFastUnorderedTimeStamps", "-Xlog:gc*=debug", "-Xmx32m", "-Xmn30m",
            "-XX:-UseDynamicNumberOfGCThreads", "-XX:MaxTenuringThreshold=0", "-XX:+UseSerialGC"});
    }
}
