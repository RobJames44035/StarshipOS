/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.gc.objectcount;

import java.util.List;
import java.util.HashMap;

import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.Events;

class Foo {
}

class Constants {
    public static final int oneMB = 1048576;
}

public class ObjectCountEventVerifier {
    public static Foo[] foos;

    public static void createTestData() {
        foos = new Foo[Constants.oneMB];
    }

    public static void verify(List<RecordedEvent> objectCountEvents) throws Exception {
        Asserts.assertFalse(objectCountEvents.isEmpty(), "Expected at least one object count event");
        // Object count events should be sent only for those classes which instances occupy over 0.5%
        // of the heap. Therefore there can't be more than 200 events
        Asserts.assertLessThanOrEqual(objectCountEvents.size(), 200, "Expected at most 200 object count events");

        HashMap<String, Long> numInstancesOfClass = new HashMap<String, Long>();
        HashMap<String, Long> sizeOfInstances = new HashMap<String, Long>();

        for (RecordedEvent event : objectCountEvents) {
            String className = Events.assertField(event, "objectClass.name").notEmpty().getValue();
            long count = Events.assertField(event, "count").atLeast(0L).getValue();
            long totalSize = Events.assertField(event, "totalSize").atLeast(1L).getValue();
            System.out.println(className);
            numInstancesOfClass.put(className, count);
            sizeOfInstances.put(className, totalSize);
        }
        System.out.println(numInstancesOfClass);
        final String fooArrayName = "[Ljdk/jfr/event/gc/objectcount/Foo;";
        Asserts.assertTrue(numInstancesOfClass.containsKey(fooArrayName), "Expected an event for the Foo array");
        Asserts.assertEquals(sizeOfInstances.get(fooArrayName), expectedFooArraySize(Constants.oneMB), "Wrong size of the Foo array");
    }

    private static long expectedFooArraySize(long count) {
        boolean runsOn32Bit = System.getProperty("sun.arch.data.model").equals("32");
        int bytesPerWord = runsOn32Bit ? 4 : 8;
        int objectHeaderSize = bytesPerWord * 3; // length will be aligned on 64 bits
        int alignmentInOopArray = runsOn32Bit ? 4 : 0;
        int ptrSize = bytesPerWord;
        return objectHeaderSize + alignmentInOopArray + count * ptrSize;
    }
}
