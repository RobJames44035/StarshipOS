/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.jfr.api.metadata.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jdk.jfr.Event;
import jdk.jfr.EventType;
import jdk.jfr.MetadataDefinition;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.metadata.annotations.TestSimpleMetadataEvent
 */
public class TestSimpleMetadataEvent {

    @MetadataDefinition
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Severity {
        int value() default 50;
    }

    @Severity
    static class MetadataEvent extends Event {
    }

    public static void main(String[] args) throws Exception {
        EventType.getEventType(MetadataEvent.class);
    }
}
