/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.gc.collection;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @requires vm.gc == "Serial" | vm.gc == null
 * @library /test/lib /test/jdk
 * @run main/othervm -Xmx50m -Xmn2m -XX:+UseSerialGC -XX:+UnlockExperimentalVMOptions -XX:-UseFastUnorderedTimeStamps -Xlog:gc+heap=trace,gc*=debug jdk.jfr.event.gc.collection.TestYoungGarbageCollectionEventWithDefNew
 */
public class TestYoungGarbageCollectionEventWithDefNew {

    public static void main(String[] args) throws Exception {
        YoungGarbageCollectionEvent.test();
    }

}
