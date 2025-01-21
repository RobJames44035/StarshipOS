/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.constantpool;

import java.lang.constant.MethodTypeDesc;

import jdk.internal.classfile.impl.AbstractPoolEntry;
import jdk.internal.classfile.impl.Util;

/**
 * Models a {@code CONSTANT_MethodRef_info} structure, or a symbolic reference
 * to a class method, in the constant pool of a {@code class} file.
 * <p>
 * A class method reference entry is composite:
 * {@snippet lang=text :
 * // @link substring="MethodRefEntry" target="ConstantPoolBuilder#methodRefEntry(ClassEntry, NameAndTypeEntry)" :
 * MethodRefEntry(
 *     ClassEntry owner, // @link substring="owner" target="#owner()"
 *     NameAndTypeEntry nameAndType // @link substring="nameAndType" target="#nameAndType()"
 * )
 * }
 * where the type in the {@code NameAndTypeEntry} is a {@linkplain #typeSymbol()
 * method descriptor} string.
 *
 * @see ConstantPoolBuilder#methodRefEntry ConstantPoolBuilder::methodRefEntry
 * @jvms 4.4.2 The {@code CONSTANT_Fieldref_info}, {@code
 *             CONSTANT_Methodref_info}, and {@code
 *             CONSTANT_InterfaceMethodref_info} Structures
 * @since 24
 */
public sealed interface MethodRefEntry extends MemberRefEntry
        permits AbstractPoolEntry.MethodRefEntryImpl {

    /**
     * {@return a symbolic descriptor for the {@linkplain #type() method type}}
     */
    default MethodTypeDesc typeSymbol() {
        return Util.methodTypeSymbol(type());
    }
}
