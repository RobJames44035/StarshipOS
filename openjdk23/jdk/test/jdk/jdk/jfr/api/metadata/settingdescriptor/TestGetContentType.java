/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
package jdk.jfr.api.metadata.settingdescriptor;

import java.util.Objects;

import jdk.jfr.EventType;
import jdk.jfr.Frequency;
import jdk.jfr.SettingDescriptor;
import jdk.jfr.Timespan;
import jdk.jfr.Timestamp;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @summary Test SettingDescriptor.getContentType()
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.api.metadata.settingdescriptor.TestGetDescription
 */
public class TestGetContentType {

    public static void main(String[] args) throws Exception {
        EventType type = EventType.getEventType(CustomEvent.class);

        SettingDescriptor plain = Events.getSetting(type, "plain");
        Asserts.assertNull(plain.getContentType());

        SettingDescriptor annotatedType = Events.getSetting(type, "annotatedType");
        Asserts.assertNull(annotatedType.getContentType(), Timestamp.class.getName());

        SettingDescriptor newName = Events.getSetting(type, "newName");
        Asserts.assertEquals(newName.getContentType(), Timespan.class.getName());

        SettingDescriptor overridden = Events.getSetting(type, "overridden");
        Asserts.assertNull(overridden.getContentType());

        SettingDescriptor protectedBase = Events.getSetting(type, "protectedBase");
        Asserts.assertEquals(protectedBase.getContentType(), Frequency.class);

        SettingDescriptor publicBase = Events.getSetting(type, "publicBase");
        Asserts.assertEquals(publicBase.getContentType(), Timestamp.class.getName());

        SettingDescriptor packageProtectedBase = Events.getSetting(type, "packageProtectedBase");
        Asserts.assertNull(packageProtectedBase.getContentType());

        CustomEvent.assertOnDisk((x, y) -> Objects.equals(x.getContentType(), y.getContentType()) ? 0 : 1);
    }
}
