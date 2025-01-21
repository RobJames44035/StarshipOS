/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.constantpool;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDesc;

import jdk.internal.classfile.impl.AbstractPoolEntry;

/**
 * Models a {@code CONSTANT_Class_info} structure, representing a reference
 * type, in the constant pool of a {@code class} file.
 * <p>
 * The use of a {@code ClassEntry} is modeled by a {@link ClassDesc} that is not
 * primitive.  Conversions are through {@link ConstantPoolBuilder#classEntry(
 * ClassDesc)} and {@link #asSymbol()}.
 * <p>
 * A {@code ClassEntry} is composite:
 * {@snippet lang=text :
 * // @link substring="ClassEntry" target="ConstantPoolBuilder#classEntry(Utf8Entry)" :
 * ClassEntry(Utf8Entry name) // @link substring="name" target="#name"
 * }
 * where {@code name} represents:
 * <ul>
 * <li>The internal form of a binary name (JVMS {@jvms 4.2.1}), if and only if
 * this {@code ClassEntry} represents a class or interface, such as {@code
 * java/lang/String} for the {@link String} class.
 * <li>A field descriptor string (JVMS {@jvms 4.3.2}) representing an array type,
 * if and only if this {@code ClassEntry} represents an array type, such as
 * {@code [I} for the {@code int[]} type, or {@code [Ljava/lang/String;} for the
 * {@code String[]} type.
 * </ul>
 * A field descriptor string for an array type can be distinguished by its
 * leading {@code '['} character.
 *
 * @apiNote
 * The internal form of a binary name, where all occurrences of {@code .} in the
 * name are replaced by {@code /}, is informally known as an <dfn>{@index
 * "internal name"}</dfn>.  This concept also applies to package names in
 * addition to class and interface names.
 *
 * @see ConstantPoolBuilder#classEntry ConstantPoolBuilder::classEntry
 * @see ClassDesc
 * @jvms 4.4.1 The {@code CONSTANT_Class_info} Structure
 * @since 24
 */
public sealed interface ClassEntry
        extends LoadableConstantEntry
        permits AbstractPoolEntry.ClassEntryImpl {

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
     * {@return the {@code Utf8Entry} referred by this structure}  If the
     * value of the UTF8 starts with a {@code [}, this represents an array type
     * and the value is a descriptor string; otherwise, this represents a class
     * or interface and the value is the {@linkplain ##internalname internal
     * form} of a binary name.
     *
     * @see ConstantPoolBuilder#classEntry(Utf8Entry)
     *      ConstantPoolBuilder::classEntry(Utf8Entry)
     */
    Utf8Entry name();

    /**
     * {@return the represented reference type, as the {@linkplain
     * ##internalname internal form} of a binary name or an array descriptor
     * string}  This is a shortcut for {@link #name() name().stringValue()}.
     */
    String asInternalName();

    /**
     * {@return the represented reference type, as a symbolic descriptor}  The
     * returned descriptor is never {@linkplain ClassDesc#isPrimitive()
     * primitive}.
     *
     * @see ConstantPoolBuilder#classEntry(ClassDesc)
     *      ConstantPoolBuilder::classEntry(ClassDesc)
     */
    ClassDesc asSymbol();
}
