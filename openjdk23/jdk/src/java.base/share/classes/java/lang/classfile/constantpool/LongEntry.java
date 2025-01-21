/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.constantpool;

import java.lang.classfile.TypeKind;

import jdk.internal.classfile.impl.AbstractPoolEntry;

/**
 * Models a {@code CONSTANT_Long_info} structure, or a {@code long} constant, in
 * the constant pool of a {@code class} file.
 * <p>
 * The use of a {@code LongEntry} is modeled by a {@code long}.  Conversions are
 * through {@link ConstantPoolBuilder#longEntry(long)} and {@link #longValue()}.
 * <p>
 * A long entry has a {@linkplain #width() width} of {@code 2}, making its
 * subsequent constant pool index valid and unusable.
 *
 * @see ConstantPoolBuilder#longEntry ConstantPoolBuilder::longEntry
 * @jvms 4.4.5 The {@code CONSTANT_Long_info} and {@code CONSTANT_Double_info}
 *             Structures
 * @since 24
 */
public sealed interface LongEntry
        extends AnnotationConstantValueEntry, ConstantValueEntry
        permits AbstractPoolEntry.LongEntryImpl {

    /**
     * {@return the {@code long} value}
     *
     * @see ConstantPoolBuilder#longEntry(long)
     *      ConstantPoolBuilder::longEntry(long)
     */
    long longValue();

    /**
     * {@return the type of the constant}
     */
    @Override
    default TypeKind typeKind() {
        return TypeKind.LONG;
    }
}
