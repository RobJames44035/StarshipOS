/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.gc.collection;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @requires vm.gc == "Parallel" | vm.gc == null
 * @library /test/lib /test/jdk
 * @run main/othervm -Xmx50m -Xmn2m -XX:+UseParallelGC -XX:+UnlockExperimentalVMOptions -XX:-UseFastUnorderedTimeStamps -XX:-UseAdaptiveSizePolicy -Xlog:gc+heap=trace,gc*=debug jdk.jfr.event.gc.collection.TestYoungGarbageCollectionEventWithParallelScavenge
 */
public class TestYoungGarbageCollectionEventWithParallelScavenge {

    public static void main(String[] args) throws Exception {
        YoungGarbageCollectionEvent.test();
    }

}
