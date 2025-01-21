/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.constantpool;

import java.lang.classfile.TypeKind;

import jdk.internal.classfile.impl.AbstractPoolEntry;

/**
 * Models a {@code CONSTANT_Double_info} structure, representing a {@code
 * double} constant, in the constant pool of a {@code class} file.
 * <p>
 * The use of a {@code DoubleEntry} is modeled by a {@code double}.  Conversions
 * are through {@link ConstantPoolBuilder#doubleEntry} and {@link #doubleValue()}.
 * In the conversions, all NaN values of the {@code double} may or may not be
 * collapsed into a single {@linkplain Double#NaN "canonical" NaN value}.
 * <p>
 * A double entry has a {@linkplain #width() width} of {@code 2}, making its
 * subsequent constant pool index valid and unusable.
 *
 * @see ConstantPoolBuilder#doubleEntry ConstantPoolBuilder::doubleEntry
 * @jvms 4.4.5 The {@code CONSTANT_Long_info} and {@code CONSTANT_Double_info}
 *             Structures
 * @since 24
 */
public sealed interface DoubleEntry
        extends AnnotationConstantValueEntry, ConstantValueEntry
        permits AbstractPoolEntry.DoubleEntryImpl {

    /**
     * {@return the {@code double} value}
     *
     * @see ConstantPoolBuilder#doubleEntry(double)
     *      ConstantPoolBuilder::doubleEntry(double)
     */
    double doubleValue();

    @Override
    default TypeKind typeKind() {
        return TypeKind.DOUBLE;
    }
}
