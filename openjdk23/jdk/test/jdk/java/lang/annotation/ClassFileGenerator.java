/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * Create class file using Class-File API, slightly modified the ASMifier output
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.classfile.Annotation;
import java.lang.classfile.AnnotationElement;
import java.lang.classfile.AnnotationValue;
import java.lang.classfile.ClassFile;
import java.lang.classfile.attribute.AnnotationDefaultAttribute;
import java.lang.classfile.attribute.ExceptionsAttribute;
import java.lang.classfile.attribute.RuntimeVisibleAnnotationsAttribute;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.lang.reflect.AccessFlag;

import static java.lang.classfile.ClassFile.ACC_ABSTRACT;
import static java.lang.classfile.ClassFile.ACC_PUBLIC;
import static java.lang.constant.ConstantDescs.CD_Exception;
import static java.lang.constant.ConstantDescs.CD_Object;
import static java.lang.constant.ConstantDescs.CD_int;
import static java.lang.constant.ConstantDescs.MTD_void;
import static java.lang.reflect.AccessFlag.ABSTRACT;
import static java.lang.reflect.AccessFlag.INTERFACE;
import static java.lang.reflect.AccessFlag.PUBLIC;

public class ClassFileGenerator {
    private static final ClassDesc CD_Annotation = java.lang.annotation.Annotation.class.describeConstable().orElseThrow();
    private static final ClassDesc CD_Retention = Retention.class.describeConstable().orElseThrow();

    public static void main(String... args) throws Exception {
        classFileWriter("AnnotationWithVoidReturn.class", AnnotationWithVoidReturnDump.dump());
        classFileWriter("AnnotationWithParameter.class", AnnotationWithParameterDump.dump());
        classFileWriter("AnnotationWithExtraInterface.class", AnnotationWithExtraInterfaceDump.dump());
        classFileWriter("AnnotationWithException.class", AnnotationWithExceptionDump.dump());
        classFileWriter("AnnotationWithHashCode.class", AnnotationWithHashCodeDump.dump());
        classFileWriter("AnnotationWithDefaultMember.class", AnnotationWithDefaultMemberDump.dump());
        classFileWriter("AnnotationWithoutAnnotationAccessModifier.class",
                        AnnotationWithoutAnnotationAccessModifierDump.dump());
        classFileWriter("HolderX.class", HolderXDump.dump());
    }

    private static void classFileWriter(String name, byte[] contents) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(new File(System.getProperty("test.classes"),
                name))) {
            fos.write(contents);
        }
    }

    /* Following code creates equivalent classfile, which is not allowed by javac:

    @Retention(RetentionPolicy.RUNTIME)
    public @interface AnnotationWithVoidReturn {
        void m() default 1;
    }

    */

    private static class AnnotationWithVoidReturnDump {
        public static byte[] dump() {
            return ClassFile.of().build(ClassDesc.of("AnnotationWithVoidReturn"), clb -> {
                clb.withSuperclass(CD_Object);
                clb.withInterfaceSymbols(CD_Annotation);
                clb.withFlags(PUBLIC, AccessFlag.ANNOTATION, ABSTRACT, AccessFlag.INTERFACE);
                clb.with(RuntimeVisibleAnnotationsAttribute.of(
                        Annotation.of(CD_Retention, AnnotationElement.of("value",
                                AnnotationValue.of(RetentionPolicy.RUNTIME)))
                ));
                clb.withMethod("m", MTD_void, ACC_PUBLIC | ACC_ABSTRACT,
                        mb -> mb.with(AnnotationDefaultAttribute.of(AnnotationValue.ofInt(1))));
            });
        }
    }

    /* Following code creates equivalent classfile, which is not allowed by javac:

    @Retention(RetentionPolicy.RUNTIME)
    public @interface AnnotationWithParameter {
        int m(int x) default -1;
    }

    */

    private static class AnnotationWithParameterDump {
        public static byte[] dump() {
            return ClassFile.of().build(ClassDesc.of("AnnotationWithParameter"), clb -> {
                clb.withSuperclass(CD_Object);
                clb.withInterfaceSymbols(CD_Annotation);
                clb.withFlags(PUBLIC, AccessFlag.ANNOTATION, ABSTRACT, AccessFlag.INTERFACE);
                clb.with(RuntimeVisibleAnnotationsAttribute.of(
                        Annotation.of(CD_Retention, AnnotationElement.of("value",
                                AnnotationValue.of(RetentionPolicy.RUNTIME)))
                ));
                clb.withMethod("m", MethodTypeDesc.of(CD_int, CD_int), ACC_PUBLIC | ACC_ABSTRACT,
                        mb -> mb.with(AnnotationDefaultAttribute.of(AnnotationValue.ofInt(-1))));
            });
        }
    }

    /* Following code creates equivalent classfile, which is not allowed by javac:

    @Retention(RetentionPolicy.RUNTIME)
    public @interface AnnotationWithExtraInterface extends java.io.Serializable {
        int m() default 1;
    }

    */

    private static class AnnotationWithExtraInterfaceDump {
        public static byte[] dump() {
            return ClassFile.of().build(ClassDesc.of("AnnotationWithExtraInterface"), clb -> {
                clb.withSuperclass(CD_Object);
                clb.withInterfaceSymbols(CD_Annotation, Serializable.class.describeConstable().orElseThrow());
                clb.withFlags(PUBLIC, AccessFlag.ANNOTATION, ABSTRACT, AccessFlag.INTERFACE);
                clb.with(RuntimeVisibleAnnotationsAttribute.of(
                        Annotation.of(CD_Retention, AnnotationElement.of("value",
                                AnnotationValue.of(RetentionPolicy.RUNTIME)))
                ));
                clb.withMethod("m", MethodTypeDesc.of(CD_int), ACC_PUBLIC | ACC_ABSTRACT,
                        mb -> mb.with(AnnotationDefaultAttribute.of(AnnotationValue.ofInt(1))));
            });
        }
    }

    /* Following code creates equivalent classfile, which is not allowed by javac:

    @Retention(RetentionPolicy.RUNTIME)
    public @interface AnnotationWithException {
        int m() throws Exception default 1;
    }

    */

    private static class AnnotationWithExceptionDump {
        public static byte[] dump() {
            return ClassFile.of().build(ClassDesc.of("AnnotationWithException"), clb -> {
                clb.withSuperclass(CD_Object);
                clb.withInterfaceSymbols(CD_Annotation);
                clb.withFlags(PUBLIC, AccessFlag.ANNOTATION, ABSTRACT, AccessFlag.INTERFACE);
                clb.with(RuntimeVisibleAnnotationsAttribute.of(
                        Annotation.of(CD_Retention, AnnotationElement.of("value",
                                AnnotationValue.of(RetentionPolicy.RUNTIME)))
                ));
                clb.withMethod("m", MethodTypeDesc.of(CD_int), ACC_PUBLIC | ACC_ABSTRACT, mb -> {
                    mb.with(AnnotationDefaultAttribute.of(AnnotationValue.ofInt(1)));
                    mb.with(ExceptionsAttribute.ofSymbols(CD_Exception));
                });
            });
        }
    }

    /* Following code creates equivalent classfile, which is not allowed by javac:

    @Retention(RetentionPolicy.RUNTIME)
    public @interface AnnotationWithHashCode {
        int hashCode() default 1;
    }

    */

    private static class AnnotationWithHashCodeDump {
        public static byte[] dump() {
            return ClassFile.of().build(ClassDesc.of("AnnotationWithHashCode"), clb -> {
                clb.withSuperclass(CD_Object);
                clb.withInterfaceSymbols(CD_Annotation);
                clb.withFlags(PUBLIC, AccessFlag.ANNOTATION, ABSTRACT, AccessFlag.INTERFACE);
                clb.with(RuntimeVisibleAnnotationsAttribute.of(
                        Annotation.of(CD_Retention, AnnotationElement.of("value",
                                AnnotationValue.of(RetentionPolicy.RUNTIME)))
                ));
                clb.withMethod("hashCode", MethodTypeDesc.of(CD_int), ACC_PUBLIC | ACC_ABSTRACT,
                        mb -> mb.with(AnnotationDefaultAttribute.of(AnnotationValue.ofInt(1))));
            });
        }
    }

    /* Following code creates equivalent classfile, which is not allowed by javac:

    @Retention(RetentionPolicy.RUNTIME)
    public @interface AnnotationWithDefaultMember {
        int m() default 1;
        default int d() default 2 { return 2; }
    }

    */

    private static class AnnotationWithDefaultMemberDump {
        public static byte[] dump() throws Exception {
            return ClassFile.of().build(ClassDesc.of("AnnotationWithDefaultMember"), clb -> {
                clb.withSuperclass(CD_Object);
                clb.withInterfaceSymbols(CD_Annotation);
                clb.withFlags(PUBLIC, AccessFlag.ANNOTATION, ABSTRACT, AccessFlag.INTERFACE);
                clb.with(RuntimeVisibleAnnotationsAttribute.of(
                        Annotation.of(CD_Retention, AnnotationElement.of("value",
                                AnnotationValue.of(RetentionPolicy.RUNTIME)))
                ));
                clb.withMethod("m", MethodTypeDesc.of(CD_int), ACC_PUBLIC | ACC_ABSTRACT,
                        mb -> mb.with(AnnotationDefaultAttribute.of(AnnotationValue.ofInt(1))));
                clb.withMethod("d", MethodTypeDesc.of(CD_int), ACC_PUBLIC, mb -> {
                    mb.with(AnnotationDefaultAttribute.of(AnnotationValue.ofInt(2)));
                    mb.withCode(cob -> {
                        cob.iconst_2();
                        cob.ireturn();
                    });
                });
            });
        }
    }

    /* Following code creates equivalent classfile, which is not allowed by javac:

    @Retention(RetentionPolicy.RUNTIME)
    public interface AnnotationWithoutAnnotationAccessModifier extends java.lang.annotation.Annotation {
        int m() default 1;
    }

    */

    private static class AnnotationWithoutAnnotationAccessModifierDump {
        public static byte[] dump() {
            return ClassFile.of().build(ClassDesc.of("AnnotationWithoutAnnotationAccessModifier"), clb -> {
                clb.withSuperclass(CD_Object);
                clb.withInterfaceSymbols(CD_Annotation);
                clb.withFlags(PUBLIC, /*AccessFlag.ANNOTATION,*/ ABSTRACT, AccessFlag.INTERFACE);
                clb.with(RuntimeVisibleAnnotationsAttribute.of(
                        Annotation.of(CD_Retention, AnnotationElement.of("value",
                                AnnotationValue.of(RetentionPolicy.RUNTIME)))
                ));
                clb.withMethod("m", MethodTypeDesc.of(CD_int), ACC_PUBLIC | ACC_ABSTRACT,
                        mb -> mb.with(AnnotationDefaultAttribute.of(AnnotationValue.ofInt(1))));
            });
        }
    }

    /* Following code creates equivalent classfile, which is not allowed by javac
       since AnnotationWithoutAnnotationAccessModifier is not marked with ACC_ANNOTATION:

    @GoodAnnotation
    @AnnotationWithoutAnnotationAccessModifier
    public interface HolderX {
    }

    */

    private static class HolderXDump {
        public static byte[] dump() {
            return ClassFile.of().build(ClassDesc.of("HolderX"), clb -> {
                clb.withSuperclass(CD_Object);
                clb.withFlags(PUBLIC, ABSTRACT, INTERFACE);
                clb.with(RuntimeVisibleAnnotationsAttribute.of(
                        Annotation.of(ClassDesc.of("GoodAnnotation")),
                        Annotation.of(ClassDesc.of("ClassFileGenerator$AnnotationWithoutAnnotationAccessModifier"))
                ));
            });
        }
    }
}
