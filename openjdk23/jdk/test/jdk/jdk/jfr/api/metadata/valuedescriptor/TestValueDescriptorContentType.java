/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.metadata.valuedescriptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jdk.jfr.ContentType;
import jdk.jfr.Event;
import jdk.jfr.EventType;
import jdk.jfr.MetadataDefinition;
import jdk.jfr.ValueDescriptor;
import jdk.test.lib.Asserts;

/**
 * @test
 * @summary Test ValueDescriptor.getContentType()
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.metadata.valuedescriptor.TestValueDescriptorContentType
 */
public class TestValueDescriptorContentType {

    @MetadataDefinition
    @ContentType
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.FIELD, ElementType.TYPE, ElementType.METHOD })
    static public @interface Hawaiian {
    }

    @MetadataDefinition
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.FIELD, ElementType.TYPE })
    static public @interface NotContentType {
    }

    @SuppressWarnings("unused")
    private static class AlohaEvent extends Event {
        @Hawaiian
        String greeting;

        String missing;

        @NotContentType
        String otherAnnotation;
    }

    public static void main(String[] args) throws Throwable {
        EventType type = EventType.getEventType(AlohaEvent.class);

        // check field annotation on event value
        ValueDescriptor filter = type.getField("greeting");
        Asserts.assertEquals(filter.getContentType(), Hawaiian.class.getName());

        // check field annotation with missing content type
        ValueDescriptor missing = type.getField("missing");
        Asserts.assertEquals(missing.getContentType(), null);

        // check field annotation with annotation but not content type
        ValueDescriptor otherAnnotation = type.getField("otherAnnotation");
        Asserts.assertEquals(otherAnnotation.getContentType(), null);
    }

}
