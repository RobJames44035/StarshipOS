/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.jfr.api.metadata.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jdk.jfr.AnnotationElement;
import jdk.jfr.Event;
import jdk.jfr.EventType;
import jdk.jfr.Label;
import jdk.jfr.MetadataDefinition;
import jdk.jfr.SettingDefinition;
import jdk.jfr.SettingDescriptor;
import jdk.jfr.ValueDescriptor;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.Events;
import jdk.test.lib.jfr.SimpleSetting;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.metadata.annotations.TestLabel
 */
public class TestLabel {

    @MetadataDefinition
    @Label("Annotation Label")
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface AnnotionWithLabel {
    }

    @AnnotionWithLabel
    @Label("Event Label")
    static class LabelEvent extends Event {
        @Label("Field Label")
        boolean labledField;

        @SettingDefinition
        @Label("Setting Label")
        boolean dummy(SimpleSetting ds) {
            return true;
        }
    }

    public static void main(String[] args) throws Exception {
        // Event
        EventType t = EventType.getEventType(LabelEvent.class);
        Asserts.assertEquals(t.getLabel(), "Event Label", "Incorrect label for event");

        // Field
        ValueDescriptor field = t.getField("labledField");
        Asserts.assertEquals(field.getLabel(), "Field Label", "Incorrect label for field");

        // Annotation
        AnnotationElement awl = Events.getAnnotationByName(t, AnnotionWithLabel.class.getName());
        Label label = awl.getAnnotation(Label.class);
        Asserts.assertEquals(label.value(), "Annotation Label", "Incorrect label for annotation");

        // Setting
        for (SettingDescriptor v: t.getSettingDescriptors()) {
            if (v.getName().equals("dummy")) {
                Label settingLabel = v.getAnnotation(Label.class);
                Asserts.assertEquals(settingLabel.value(), "Setting Label", "Incorrect label for setting");
            }
        }
    }
}
