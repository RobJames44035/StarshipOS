/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.constantpool;

import java.lang.constant.ConstantDesc;
import java.lang.constant.MethodTypeDesc;

import jdk.internal.classfile.impl.AbstractPoolEntry;

/**
 * Models a {@code CONSTANT_MethodType_info} structure, or a symbolic reference
 * to a method type, in the constant pool of a {@code class} file.
 * <p>
 * The use of a {@code MethodTypeEntry} is modeled by a {@link MethodTypeDesc}.
 * Conversions are through {@link ConstantPoolBuilder#methodTypeEntry(MethodTypeDesc)}
 * and {@link #asSymbol()}.
 * <p>
 * A method type entry is composite:
 * {@snippet lang=text :
 * // @link substring="MethodTypeEntry" target="ConstantPoolBuilder#methodTypeEntry(Utf8Entry)" :
 * MethodTypeEntry(Utf8Entry descriptor) // @link substring="descriptor" target="#descriptor()"
 * }
 * where {@code descriptor} is a {@linkplain #asSymbol() method descriptor}
 * string.
 *
 * @jvms 4.4.9 The {@code CONSTANT_MethodType_info} Structure
 * @since 24
 */
public sealed interface MethodTypeEntry
        extends LoadableConstantEntry
        permits AbstractPoolEntry.MethodTypeEntryImpl {

    /**
     * {@inheritDoc}
     * <p>
     * This is equivalent to {@link #asSymbol() asSymbol()}.
     */
    @Override
    default ConstantDesc constantValue() {
        return asSymbol();
    }

    /**
     * {@return the {@linkplain #asSymbol() method descriptor} string}
     */
    Utf8Entry descriptor();

    /**
     * {@return a symbolic descriptor for the {@linkplain #descriptor() method
     * type}}
     */
    MethodTypeDesc asSymbol();
}
