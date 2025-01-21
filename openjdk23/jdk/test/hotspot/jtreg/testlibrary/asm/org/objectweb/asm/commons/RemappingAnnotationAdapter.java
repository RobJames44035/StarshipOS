/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.objectweb.asm.commons;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

/**
 * An {@link AnnotationVisitor} adapter for type remapping.
 *
 * @deprecated use {@link AnnotationRemapper} instead.
 * @author Eugene Kuleshov
 */
@Deprecated
public class RemappingAnnotationAdapter extends AnnotationVisitor {

    protected final Remapper remapper;

    public RemappingAnnotationAdapter(
            final AnnotationVisitor annotationVisitor, final Remapper remapper) {
        this(Opcodes.ASM9, annotationVisitor, remapper);
    }

    protected RemappingAnnotationAdapter(
            final int api, final AnnotationVisitor annotationVisitor, final Remapper remapper) {
        super(api, annotationVisitor);
        this.remapper = remapper;
    }

    @Override
    public void visit(final String name, final Object value) {
        av.visit(name, remapper.mapValue(value));
    }

    @Override
    public void visitEnum(final String name, final String descriptor, final String value) {
        av.visitEnum(name, remapper.mapDesc(descriptor), value);
    }

    @Override
    public AnnotationVisitor visitAnnotation(final String name, final String descriptor) {
        AnnotationVisitor annotationVisitor = av.visitAnnotation(name, remapper.mapDesc(descriptor));
        return annotationVisitor == null
                ? null
                : (annotationVisitor == av
                        ? this
                        : new RemappingAnnotationAdapter(annotationVisitor, remapper));
    }

    @Override
    public AnnotationVisitor visitArray(final String name) {
        AnnotationVisitor annotationVisitor = av.visitArray(name);
        return annotationVisitor == null
                ? null
                : (annotationVisitor == av
                        ? this
                        : new RemappingAnnotationAdapter(annotationVisitor, remapper));
    }
}

