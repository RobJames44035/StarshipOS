/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.constantpool;

import java.lang.classfile.TypeKind;

import jdk.internal.classfile.impl.AbstractPoolEntry;

/**
 * Models a {@code CONSTANT_Float_info} structure, or a {@code float} constant,
 * in the constant pool of a {@code class} file.
 * <p>
 * The use of a {@code FloatEntry} is modeled by a {@code float}.  Conversions
 * are through {@link ConstantPoolBuilder#floatEntry} and {@link #floatValue()}.
 * In the conversions, all NaN values of the {@code float} may or may not be
 * collapsed into a single {@linkplain Float#NaN "canonical" NaN value}.
 *
 * @see ConstantPoolBuilder#floatEntry ConstantPoolBuilder::floatEntry
 * @jvms 4.4.4 The {@code CONSTANT_Integer_info} and {@code CONSTANT_Float_info}
 *             Structures
 * @since 24
 */
public sealed interface FloatEntry
        extends AnnotationConstantValueEntry, ConstantValueEntry
        permits AbstractPoolEntry.FloatEntryImpl {

    /**
     * {@return the {@code float} value}
     *
     * @see ConstantPoolBuilder#floatEntry(float)
     */
    float floatValue();

    @Override
    default TypeKind typeKind() {
        return TypeKind.FLOAT;
    }
}
