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
import jdk.jfr.MetadataDefinition;
import jdk.jfr.ValueDescriptor;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.metadata.annotations.TestMetadata
 */
public class TestMetadata {

    @Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface OtherAnnotation {
    }

    @MetadataDefinition
    @SecondJFRAnnotation
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface FirstJFRAnnotation {
    }

    @MetadataDefinition
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface SecondJFRAnnotation {
    }

    @FirstJFRAnnotation
    @OtherAnnotation
    static class MetadataEvent extends Event {
        @OtherAnnotation
        @FirstJFRAnnotation
        String field;
    }

    public static void main(String[] args) throws Exception {
        EventType t = EventType.getEventType(MetadataEvent.class);
        ValueDescriptor field = t.getField("field");
        Events.hasAnnotation(field, FirstJFRAnnotation.class);
        Asserts.assertTrue(field.getAnnotation(OtherAnnotation.class) == null, "Only annotation annotated with @Metadata should exist");

        AnnotationElement a = Events.getAnnotationByName(t, FirstJFRAnnotation.class.getName());
        Asserts.assertTrue(a.getAnnotation(SecondJFRAnnotation.class) != null , "Annotations with @Metadata should be followed for indirect annotations");
    }
}
