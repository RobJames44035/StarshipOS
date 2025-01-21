/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
package jdk.jfr.api.metadata.settingdescriptor;

import java.util.Objects;

import jdk.jfr.EventType;
import jdk.jfr.SettingDescriptor;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @summary Test SettingDescriptor.getDescription()
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.api.metadata.settingdescriptor.TestGetDescription
 */
public class TestGetDescription {

    public static void main(String[] args) throws Exception {
        EventType type = EventType.getEventType(CustomEvent.class);

        SettingDescriptor plain = Events.getSetting(type, "plain");
        Asserts.assertNull(plain.getDescription());

        SettingDescriptor annotatedType = Events.getSetting(type, "annotatedType");
        Asserts.assertEquals(annotatedType.getDescription(), AnnotatedSetting.DESCRIPTION);

        SettingDescriptor newName = Events.getSetting(type, "newName");
        Asserts.assertEquals(newName.getDescription(), CustomEvent.DESCRIPTION_OF_AN_ANNOTATED_METHOD);

        SettingDescriptor overridden = Events.getSetting(type, "overridden");
        Asserts.assertNull(overridden.getDescription());

        SettingDescriptor protectedBase = Events.getSetting(type, "protectedBase");
        Asserts.assertEquals(protectedBase.getDescription(), "Description of protected base");

        SettingDescriptor publicBase = Events.getSetting(type, "publicBase");
        Asserts.assertEquals(publicBase.getDescription(), AnnotatedSetting.DESCRIPTION);

        SettingDescriptor packageProtectedBase = Events.getSetting(type, "packageProtectedBase");
        Asserts.assertNull(packageProtectedBase.getDescription());

        CustomEvent.assertOnDisk((x, y) -> Objects.equals(x.getDescription(), y.getDescription()) ? 0 : 1);
    }
}
