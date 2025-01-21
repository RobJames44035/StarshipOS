/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.constantpool;

import java.lang.constant.ClassDesc;

import jdk.internal.classfile.impl.AbstractPoolEntry;
import jdk.internal.classfile.impl.Util;

/**
 * Models a {@code CONSTANT_Fieldref_info} structure, or a symbolic reference
 * to a field, in the constant pool of a {@code class} file.
 * <p>
 * A field reference constant pool entry is composite:
 * {@snippet lang=text :
 * // @link substring="FieldRefEntry" target="ConstantPoolBuilder#fieldRefEntry(ClassEntry, NameAndTypeEntry)" :
 * FieldRefEntry(
 *     ClassEntry owner, // @link substring="owner" target="#owner()"
 *     NameAndTypeEntry nameAndType // @link substring="nameAndType" target="#nameAndType()"
 * )
 * }
 * where the {@link #type() nameAndType.type()} represents a {@linkplain
 * #typeSymbol() field descriptor} string.
 *
 * @see ConstantPoolBuilder#fieldRefEntry ConstantPoolBuilder::fieldRefEntry
 * @jvms 4.4.2 The {@code CONSTANT_Fieldref_info}, {@code
 *             CONSTANT_Methodref_info}, and {@code
 *             CONSTANT_InterfaceMethodref_info} Structures
 * @since 24
 */
public sealed interface FieldRefEntry extends MemberRefEntry
        permits AbstractPoolEntry.FieldRefEntryImpl {

    /**
     * {@return a symbolic descriptor for the {@linkplain #type() field type}}
     */
    default ClassDesc typeSymbol() {
        return Util.fieldTypeSymbol(type());
    }
}
