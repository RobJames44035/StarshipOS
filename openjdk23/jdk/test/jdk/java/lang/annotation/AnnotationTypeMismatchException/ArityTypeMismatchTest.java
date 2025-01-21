/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8266791
 * @summary Annotation property which is compiled as an array property but
 *          changed observed as a singular element should throw an
 *          AnnotationTypeMismatchException
 * @run main ArityTypeMismatchTest
 */

import java.lang.annotation.AnnotationTypeMismatchException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.classfile.Annotation;
import java.lang.classfile.AnnotationElement;
import java.lang.classfile.AnnotationValue;
import java.lang.classfile.ClassFile;
import java.lang.classfile.attribute.RuntimeVisibleAnnotationsAttribute;
import java.lang.constant.ClassDesc;

import static java.lang.constant.ConstantDescs.CD_Object;

public class ArityTypeMismatchTest {

    public static void main(String[] args) throws Exception {
        /*
         * This test creates an annotation with a member with a non-array type where the annotation
         * defines an array property of this type. This can happen if the annotation class is recompiled
         * without recompiling the code that declares an annotation of this type. In the example, a
         * class is defined to be annotated as
         *
         * @AnAnnotation(value = {"v"}) // should no longer be an array
         * class Carrier { }
         *
         * where @AnAnnotation expects a singular value.
         */
        byte[] b = ClassFile.of().build(ClassDesc.of("sample", "Carrier"), clb -> {
            clb.withSuperclass(CD_Object);
            clb.with(RuntimeVisibleAnnotationsAttribute.of(
                    Annotation.of(
                            AnAnnotation.class.describeConstable().orElseThrow(),
                            AnnotationElement.of("value", AnnotationValue.of(new String[] {"v"}))
                    )
            ));
        });
        ByteArrayClassLoader cl = new ByteArrayClassLoader(ArityTypeMismatchTest.class.getClassLoader());
        cl.init(b);
        AnAnnotation sample = cl.loadClass("sample.Carrier").getAnnotation(AnAnnotation.class);
        try {
            String value = sample.value();
            throw new IllegalStateException("Found value: " + value);
        } catch (AnnotationTypeMismatchException e) {
            if (!e.element().getName().equals("value")) {
                throw new IllegalStateException("Unexpected element: " + e.element());
            } else if (!e.foundType().equals("Array with component tag: s")) {
                throw new IllegalStateException("Unexpected type: " + e.foundType());
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface AnAnnotation {
        String value();
    }

    public static class ByteArrayClassLoader extends ClassLoader {

        public ByteArrayClassLoader(ClassLoader parent) {
            super(parent);
        }

        public void init(byte[] b) {
            defineClass("sample.Carrier", b, 0, b.length);
        }
    }
}
