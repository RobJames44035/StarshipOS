/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.jmx.info;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.jfr.jmx.JmxHelper;

import jdk.jfr.EventType;
import jdk.jfr.FlightRecorder;
import jdk.management.jfr.EventTypeInfo;
import jdk.management.jfr.FlightRecorderMXBean;
import jdk.test.lib.Asserts;

/**
 * @test
 * @key jfr
 * @summary Test for EventTypeInfo
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm -Djdk.attach.allowAttachSelf=true -Dcom.sun.management.jmxremote jdk.jfr.jmx.info.TestEventTypeInfo
 */
public class TestEventTypeInfo {
    public static void main(String[] args) throws Throwable {
        FlightRecorder jfr = FlightRecorder.getFlightRecorder();

        long selfPID = JmxHelper.getPID();
        FlightRecorderMXBean bean = JmxHelper.getFlighteRecorderMXBean(selfPID);
        List<EventTypeInfo> typeInfos = bean.getEventTypes();

        Map<String, EventType> types = new HashMap<>();
        for (EventType type : jfr.getEventTypes()) {
            types.put(type.getName(), type);
        }

        Asserts.assertFalse(typeInfos.isEmpty(), "No EventTypeInfos found");
        Asserts.assertFalse(types.isEmpty(), "No EventTypes found");

        for (EventTypeInfo typeInfo : typeInfos) {
            final String key = typeInfo.getName();
            System.out.println("EventType name = " + key);
            EventType type = types.get(key);
            Asserts.assertNotNull(type, "No EventType for name " + key);
            types.remove(key);

            Asserts.assertEquals(typeInfo.getCategoryNames(), type.getCategoryNames(), "Wrong category");
            Asserts.assertEquals(typeInfo.getDescription(), type.getDescription(), "Wrong description");
            Asserts.assertEquals(typeInfo.getId(), type.getId(), "Wrong id");
            Asserts.assertEquals(typeInfo.getLabel(), type.getLabel(), "Wrong label");
            Asserts.assertEquals(typeInfo.getName(), type.getName(), "Wrong name");

            JmxHelper.verifyEventSettingsEqual(type, typeInfo);
        }

        // Verify that all EventTypes have been matched.
        if (!types.isEmpty()) {
            for (String name : types.keySet()) {
                System.out.println("Found extra EventType with name " + name);
            }
            Asserts.fail("Found extra EventTypes");
        }
    }

}
