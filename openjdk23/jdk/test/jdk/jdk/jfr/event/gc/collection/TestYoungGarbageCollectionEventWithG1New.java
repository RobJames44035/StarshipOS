/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.gc.collection;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @requires vm.gc == "G1" | vm.gc == null
 * @library /test/lib /test/jdk
 * @run main/othervm -Xmx50m -Xmn2m -XX:+UseG1GC -XX:+UnlockExperimentalVMOptions -XX:-UseFastUnorderedTimeStamps -Xlog:gc+heap=trace,gc*=debug jdk.jfr.event.gc.collection.TestYoungGarbageCollectionEventWithG1New
 */
public class TestYoungGarbageCollectionEventWithG1New {

    public static void main(String[] args) throws Exception {
        YoungGarbageCollectionEvent.test();
    }

}
