/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.constantpool;

import java.lang.classfile.Opcode;

import jdk.internal.classfile.impl.AbstractPoolEntry;

/**
 * Superinterface modeling symbolic references to a member of a class or interface
 * in the constant pool of a {@code class} file, which include references to
 * {@linkplain FieldRefEntry fields}, {@linkplain MethodRefEntry class methods},
 * and {@linkplain InterfaceMethodRefEntry interface methods}.
 * <p>
 * Different types of symbolic references to a member of a class or interface
 * bear structural similarities and share parts of the resolution processes, and
 * they can sometimes appear in the same locations.  For example, both {@link
 * MethodRefEntry} and {@link InterfaceMethodRefEntry} can appear in an {@link
 * Opcode#INVOKESTATIC invokestatic} instruction.
 * <p>
 * A member reference entry is composite:
 * {@snippet lang=text :
 * MemberRefEntry(
 *     ClassEntry owner, // @link substring="owner" target="#owner()"
 *     NameAndTypeEntry nameAndType // @link substring="nameAndType" target="#nameAndType()"
 * )
 * }
 *
 * @jvms 4.4.2 The {@code CONSTANT_Fieldref_info}, {@code
 *             CONSTANT_Methodref_info}, and {@code
 *             CONSTANT_InterfaceMethodref_info} Structures
 * @sealedGraph
 * @since 24
 */
public sealed interface MemberRefEntry extends PoolEntry
        permits FieldRefEntry, InterfaceMethodRefEntry, MethodRefEntry, AbstractPoolEntry.AbstractMemberRefEntry {
    /**
     * {@return the class or interface which this member belongs to}
     */
    ClassEntry owner();

    /**
     * {@return the name and descriptor string of the member}
     */
    NameAndTypeEntry nameAndType();

    /**
     * {@return the name of the member}
     */
    default Utf8Entry name() {
        return nameAndType().name();
    }

    /**
     * {@return the descriptor string of the member}  This is a field descriptor
     * string if this entry is a {@link FieldRefEntry}, or a method descriptor
     * string if this entry is a {@link MethodRefEntry} or {@link
     * InterfaceMethodRefEntry}.
     *
     * @apiNote
     * Each subinterface defines a {@code typeSymbol()} accessor for the
     * symbolic descriptor for the member type.
     */
    default Utf8Entry type() {
        return nameAndType().type();
    }
}
