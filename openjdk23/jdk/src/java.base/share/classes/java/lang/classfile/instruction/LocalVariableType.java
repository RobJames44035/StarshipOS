/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.instruction;

import java.lang.classfile.*;
import java.lang.classfile.attribute.LocalVariableTypeInfo;
import java.lang.classfile.attribute.LocalVariableTypeTableAttribute;
import java.lang.classfile.constantpool.Utf8Entry;

import jdk.internal.classfile.impl.AbstractPseudoInstruction;
import jdk.internal.classfile.impl.BoundLocalVariableType;
import jdk.internal.classfile.impl.TemporaryConstantPool;

/**
 * A pseudo-instruction which models a single entry in the {@link
 * LocalVariableTypeTableAttribute LocalVariableTypeTable} attribute.  Delivered
 * as a {@link CodeElement} during traversal of the elements of a {@link CodeModel},
 * according to the setting of the {@link ClassFile.DebugElementsOption} option.
 * <p>
 * A local variable type entry is composite:
 * {@snippet lang=text :
 * // @link substring="LocalVariableType" target="#of(int, String, Signature, Label, Label)" :
 * LocalVariableType(
 *     int slot, // @link substring="slot" target="#slot"
 *     String name, // @link substring="name" target="#name"
 *     Signature signature, // @link substring="signature" target="#signatureSymbol"
 *     Label startScope, // @link substring="startScope" target="#startScope"
 *     Label endScope // @link substring="endScope" target="#endScope"
 * )
 * }
 * Where {@code slot} is within {@code [0, 65535]}.
 * <p>
 * Another model, {@link LocalVariableTypeInfo}, also models a local variable
 * type entry; it has no dependency on a {@code CodeModel} and represents of bci
 * values as {@code int}s instead of {@code Label}s, and is used as components
 * of a {@link LocalVariableTypeTableAttribute}.
 *
 * @apiNote
 * {@code LocalVariableType} is used if a local variable has a parameterized
 * type, a type argument, or an array type of one of the previous types as its
 * type.  A {@link LocalVariable} with the erased type should still be created
 * for that local variable.
 *
 * @see LocalVariableTypeInfo
 * @see CodeBuilder#localVariableType CodeBuilder::localVariableType
 * @see ClassFile.DebugElementsOption
 * @since 24
 */
public sealed interface LocalVariableType extends PseudoInstruction
        permits AbstractPseudoInstruction.UnboundLocalVariableType, BoundLocalVariableType {
    /**
     * {@return the local variable slot}
     * The value is within {@code [0, 65535]}.
     */
    int slot();

    /**
     * {@return the local variable name}
     */
    Utf8Entry name();

    /**
     * {@return the local variable generic signature string}
     *
     * @apiNote
     * A symbolic generic signature of the local variable is available
     * through {@link #signatureSymbol() signatureSymbol()}.
     */
    Utf8Entry signature();

    /**
     * {@return the local variable generic signature}
     */
    default Signature signatureSymbol() {
        return Signature.parseFrom(signature().stringValue());
    }

    /**
     * {@return the start range of the local variable scope}
     */
    Label startScope();

    /**
     * {@return the end range of the local variable scope}
     */
    Label endScope();

    /**
     * {@return a local variable type pseudo-instruction}
     * {@code slot} must be within {@code [0, 65535]}.
     *
     * @param slot the local variable slot
     * @param nameEntry the local variable name
     * @param signatureEntry the local variable signature
     * @param startScope the start range of the local variable scope
     * @param endScope the end range of the local variable scope
     * @throws IllegalArgumentException if {@code slot} is out of range
     */
    static LocalVariableType of(int slot, Utf8Entry nameEntry, Utf8Entry signatureEntry, Label startScope, Label endScope) {
        return new AbstractPseudoInstruction.UnboundLocalVariableType(slot, nameEntry, signatureEntry,
                                                                      startScope, endScope);
    }

    /**
     * {@return a local variable type pseudo-instruction}
     * {@code slot} must be within {@code [0, 65535]}.
     *
     * @param slot the local variable slot
     * @param name the local variable name
     * @param signature the local variable signature
     * @param startScope the start range of the local variable scope
     * @param endScope the end range of the local variable scope
     * @throws IllegalArgumentException if {@code slot} is out of range
     */
    static LocalVariableType of(int slot, String name, Signature signature, Label startScope, Label endScope) {
        return of(slot,
                  TemporaryConstantPool.INSTANCE.utf8Entry(name),
                  TemporaryConstantPool.INSTANCE.utf8Entry(signature.signatureString()),
                  startScope, endScope);
    }
}
