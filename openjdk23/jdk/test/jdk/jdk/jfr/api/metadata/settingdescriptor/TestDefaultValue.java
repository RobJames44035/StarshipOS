/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
package jdk.jfr.api.metadata.settingdescriptor;

import jdk.jfr.EventType;
import jdk.jfr.SettingDescriptor;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @summary Test SettingDescriptor.getName()
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.api.metadata.settingdescriptor.TestDefaultValue
 */
public class TestDefaultValue {

    public static void main(String[] args) throws Exception {
        EventType type = EventType.getEventType(CustomEvent.class);

        SettingDescriptor plain = Events.getSetting(type, "plain");
        Asserts.assertEquals(plain.getDefaultValue(), "plain");

        SettingDescriptor annotatedType = Events.getSetting(type, "annotatedType");
        Asserts.assertEquals(annotatedType.getDefaultValue(), AnnotatedSetting.DEFAULT_VALUE);

        SettingDescriptor newName = Events.getSetting(type, "newName");
        Asserts.assertEquals(newName.getDefaultValue(), AnnotatedSetting.DEFAULT_VALUE);

        SettingDescriptor overridden = Events.getSetting(type, "overridden");
        Asserts.assertEquals(overridden.getDefaultValue(), PlainSetting.DEFAULT_VALUE);

        CustomEvent.assertOnDisk((x, y) -> x.getName().compareTo(y.getName()));
    }
}
