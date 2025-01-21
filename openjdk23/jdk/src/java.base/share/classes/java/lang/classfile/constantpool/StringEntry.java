/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.constantpool;

import jdk.internal.classfile.impl.AbstractPoolEntry;

/**
 * Models a {@code CONSTANT_String_info} structure, or a string constant, in the
 * constant pool of a {@code class} file.
 * <p>
 * The use of a {@code StringEntry} is represented by a {@link String}.
 * Conversions are through {@link ConstantPoolBuilder#stringEntry(String)} and
 * {@link #stringValue()}.
 * <p>
 * A string entry is composite:
 * {@snippet lang=text :
 * // @link substring="StringEntry" target="ConstantPoolBuilder#stringEntry(Utf8Entry)" :
 * StringEntry(Utf8Entry utf8) // @link substring="utf8" target="#utf8()"
 * }
 *
 * @jvms 4.4.3 The {@code CONSTANT_String_info} Structure
 * @since 24
 */
public sealed interface StringEntry
        extends ConstantValueEntry
        permits AbstractPoolEntry.StringEntryImpl {
    /**
     * {@return the UTF constant pool entry describing the string contents}
     *
     * @see ConstantPoolBuilder#stringEntry(Utf8Entry)
     */
    Utf8Entry utf8();

    /**
     * {@return the string value for this entry}
     *
     * @see ConstantPoolBuilder#stringEntry(String)
     */
    String stringValue();
}
