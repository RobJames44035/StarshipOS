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
import jdk.jfr.Experimental;
import jdk.jfr.MetadataDefinition;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.metadata.annotations.TestExperimental
 */
public class TestExperimental {

    @MetadataDefinition
    @Experimental
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface ExperimentalAnnotation {
    }

    @ExperimentalAnnotation
    @Experimental
    static class ExperimentalEvent extends Event {
        @Experimental
        boolean experimentalField;
    }

    public static void main(String[] args) throws Exception {
        EventType t = EventType.getEventType(ExperimentalEvent.class);

        // @Experimental on event
        Experimental e = t.getAnnotation(Experimental.class);
        Asserts.assertTrue(e != null, "Expected @Experimental annotation on event");

        // @Experimental on annotation
        AnnotationElement a = Events.getAnnotationByName(t, ExperimentalAnnotation.class.getName());
        e = a.getAnnotation(Experimental.class);
        Asserts.assertTrue(e != null, "Expected @Experimental on annotation");

        // @Experimental on field
        a = Events.getAnnotation(t.getField("experimentalField"), Experimental.class);
        Asserts.assertTrue(e != null, "Expected @Experimental on field");
    }
}
