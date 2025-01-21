/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.jfr.api.metadata.annotations;

import jdk.jfr.AnnotationElement;
import jdk.jfr.Label;
import jdk.jfr.MetadataDefinition;
import jdk.jfr.Timestamp;
import jdk.test.lib.Asserts;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.metadata.annotations.TestHasValue
 */

@MetadataDefinition
@interface CustomAnnotation {
    String value();
}

public class TestHasValue {

    public static void main(String[] args) throws Exception {

        testHasValue(Label.class);
        testHasValue(Timestamp.class);
        testHasValue(CustomAnnotation.class);
    }

    private static void testHasValue(Class<? extends java.lang.annotation.Annotation> clz) {

        System.out.println("class=" + clz);

        AnnotationElement a = new AnnotationElement(clz, "value");
        Asserts.assertTrue(a.hasValue("value"));
        Asserts.assertFalse(a.hasValue("nosuchvalue"));

    }
}
