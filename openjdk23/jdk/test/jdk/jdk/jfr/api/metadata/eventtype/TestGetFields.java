/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.metadata.eventtype;

import java.util.ArrayList;
import java.util.List;

import jdk.jfr.Event;
import jdk.jfr.EventType;
import jdk.jfr.ValueDescriptor;
import jdk.test.lib.Asserts;

/**
 * @test
 * @summary Test getFields()
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.metadata.eventtype.TestGetFields
 */
public class TestGetFields {

    public static void main(String[] args) throws Throwable {
        List<String> actuals = new ArrayList<>();
        EventType type = EventType.getEventType(MyEvent.class);
        for (ValueDescriptor d : type.getFields()) {
            if (d.getName().startsWith("my")) {
                String s = getCompareString(d);
                System.out.println("Actual: " + s);
                actuals.add(s);
            }
        }

        String[] expected = {
            "name=myByte; typename=byte",
            "name=myInt; typename=int",
            "name=myString; typename=java.lang.String",
            "name=myClass; typename=java.lang.Class",
            "name=myThread; typename=java.lang.Thread"
        };
        for (String s : expected) {
            Asserts.assertTrue(actuals.contains(s), "Missing expected value " + s);
        }
        Asserts.assertEquals(expected.length, actuals.size(), "Wrong number of fields found");
    }

    private static String getCompareString(ValueDescriptor d) {
        return String.format("name=%s; typename=%s",
                              d.getName(),
                              d.getTypeName());
    }

    @SuppressWarnings("unused")
    private static class MyEvent extends Event {
        public byte myByte;
        private int myInt;
        protected String myString;
        public static int myStatic; // Should not be included
        @SuppressWarnings("rawtypes")
        public Class myClass;
        public Thread myThread;
    }
}
