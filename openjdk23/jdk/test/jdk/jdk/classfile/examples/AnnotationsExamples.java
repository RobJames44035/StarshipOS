/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @summary Testing ClassFile AnnotationsExamples compilation.
 * @compile AnnotationsExamples.java
 */
import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;

import java.lang.classfile.Annotation;
import java.lang.classfile.Attributes;
import java.lang.classfile.ClassBuilder;
import java.lang.classfile.ClassElement;
import java.lang.classfile.ClassModel;
import java.lang.classfile.ClassTransform;
import java.lang.classfile.ClassFile;
import java.lang.classfile.attribute.RuntimeVisibleAnnotationsAttribute;
import java.lang.classfile.constantpool.ConstantPoolBuilder;
import jdk.internal.classfile.components.ClassPrinter;

public class AnnotationsExamples {

    /** Add a single annotation to a class using a builder convenience */
    public byte[] addAnno(ClassModel m) {
        // @@@ Not correct
        List<Annotation> annos = List.of(Annotation.of(ClassDesc.of("java.lang.FunctionalInterface")));
        return ClassFile.of().transformClass(m, ClassTransform.endHandler(cb -> cb.with(RuntimeVisibleAnnotationsAttribute.of(annos))));
    }

    /**
     * Find classes with annotations of a certain type
     */
    public void findAnnotation(ClassModel m) {
        var rvaa = m.findAttribute(Attributes.runtimeVisibleAnnotations());
        if (rvaa.isPresent()) {
            RuntimeVisibleAnnotationsAttribute a = rvaa.get();
            for (Annotation ann : a.annotations()) {
                if (ann.className().stringValue().equals("Ljava/lang/FunctionalInterface;"))
                    System.out.println(m.thisClass().asInternalName());
            }
        }
    }

    /**
     * Find classes with a specific annotation and create a new byte[] with that annotation swapped for @Deprecated.
     */
    public void swapAnnotation(ClassModel m) {
        ClassModel m2 = m;
        var rvaa = m.findAttribute(Attributes.runtimeVisibleAnnotations());
        if (rvaa.isPresent()) {
            RuntimeVisibleAnnotationsAttribute a = rvaa.get();
            var cc = ClassFile.of();
            for (Annotation ann : a.annotations()) {
                if (ann.className().stringValue().equals("Ljava/lang/annotation/Documented;")) {
                    m2 = cc.parse(cc.transformClass(m, SWAP_ANNO_TRANSFORM));
                }
            }
        }
        rvaa = m2.findAttribute(Attributes.runtimeVisibleAnnotations());
        if (rvaa.isPresent()) {
            RuntimeVisibleAnnotationsAttribute a = rvaa.get();
            for (Annotation ann : a.annotations()) {
                if (ann.className().stringValue().equals("Ljava/lang/annotation/Documented;"))
                    throw new RuntimeException();
            }
        }
    }

    //where
    private static final ClassTransform SWAP_ANNO_TRANSFORM = (cb, ce) -> {
        switch (ce) {
            case RuntimeVisibleAnnotationsAttribute attr -> {
                List<Annotation> old = attr.annotations();
                List<Annotation> newAnnos = new ArrayList<>(old.size());
                for (Annotation ann : old) {
                    if (ann.className().stringValue().equals("Ljava/lang/annotation/Documented;")) {
                        newAnnos.add(Annotation.of(ClassDesc.of("java.lang.Deprecated"), List.of()));
                    }
                    else
                        newAnnos.add(ann);
                }
                cb.with(RuntimeVisibleAnnotationsAttribute.of(newAnnos));
            }
            default -> cb.with(ce);
        }
    };

    /**
     * Find classes with a specific annotation and create a new byte[] with the same content except also adding a new annotation
     */
    public void addAnnotation(ClassModel m) {
        ClassModel m2 = m;
        var rvaa = m.findAttribute(Attributes.runtimeVisibleAnnotations());
        if (rvaa.isPresent()) {
            RuntimeVisibleAnnotationsAttribute a = rvaa.get();
            var cc = ClassFile.of();
            for (Annotation ann : a.annotations()) {
                if (ann.className().stringValue().equals("Ljava/lang/FunctionalInterface;")) {
                    m2 = cc.parse(cc.transformClass(m, (cb, ce) -> {
                        if (ce instanceof RuntimeVisibleAnnotationsAttribute ra) {
                            var oldAnnos = ra.annotations();
                            List<Annotation> newAnnos = new ArrayList<>(oldAnnos.size() + 1);
                            for (Annotation aa :oldAnnos)
                                newAnnos.add(aa);
                            ConstantPoolBuilder cpb = cb.constantPool();
                            newAnnos.add(Annotation.of(ClassDesc.of("java.lang.Deprecated"), List.of()));
                            cb.with(RuntimeVisibleAnnotationsAttribute.of(newAnnos));
                        } else {
                            cb.with(ce);
                        }
                    }));
                }
            }
        }

        int size = m2.findAttribute(Attributes.runtimeVisibleAnnotations()).orElseThrow().annotations().size();
        if (size !=2) {
            StringBuilder sb = new StringBuilder();
            ClassPrinter.toJson(m2, ClassPrinter.Verbosity.TRACE_ALL, sb::append);
            System.err.println(sb.toString());
        }
    }

    public byte[] viaEndHandlerClassBuilderEdition(ClassModel m) {
        return ClassFile.of().transformClass(m, ClassTransform.ofStateful(() -> new ClassTransform() {
            boolean found = false;

            @Override
            public void accept(ClassBuilder cb, ClassElement ce) {
                switch (ce) {
                    case RuntimeVisibleAnnotationsAttribute rvaa -> {
                        found = true;
                        List<Annotation> newAnnotations = new ArrayList<>(rvaa.annotations().size() + 1);
                        newAnnotations.addAll(rvaa.annotations());
                        newAnnotations.add(Annotation.of(ClassDesc.of("Foo")));
                        cb.with(RuntimeVisibleAnnotationsAttribute.of(newAnnotations));
                    }
                    default -> cb.with(ce);
                }
            }

            @Override
            public void atEnd(ClassBuilder builder) {
                if (!found) {
                    builder.with(RuntimeVisibleAnnotationsAttribute.of(List.of(Annotation.of(ClassDesc.of("Foo")))));
                }
            }
        }));
    }

    public byte[] viaEndHandlerClassTransformEdition(ClassModel m) {
        return ClassFile.of().transformClass(m, ClassTransform.ofStateful(() -> new ClassTransform() {
            boolean found = false;

            @Override
            public void accept(ClassBuilder cb, ClassElement ce) {
                if (ce instanceof RuntimeVisibleAnnotationsAttribute rvaa) {
                    found = true;
                    List<Annotation> newAnnotations = new ArrayList<>(rvaa.annotations().size() + 1);
                    newAnnotations.addAll(rvaa.annotations());
                    newAnnotations.add(Annotation.of(ClassDesc.of("Foo")));

                    cb.with(RuntimeVisibleAnnotationsAttribute.of(newAnnotations));
                }
                else
                    cb.with(ce);
            }

            @Override
            public void atEnd(ClassBuilder builder) {
                if (!found) {
                    builder.with(RuntimeVisibleAnnotationsAttribute.of(List.of(Annotation.of(ClassDesc.of("Foo")))));
                }
            }
        }));
    }
}