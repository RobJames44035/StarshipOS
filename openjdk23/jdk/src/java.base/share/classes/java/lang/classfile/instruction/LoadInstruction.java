/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.instruction;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.CodeElement;
import java.lang.classfile.CodeModel;
import java.lang.classfile.Instruction;
import java.lang.classfile.Opcode;
import java.lang.classfile.TypeKind;

import jdk.internal.classfile.impl.AbstractInstruction;
import jdk.internal.classfile.impl.BytecodeHelpers;
import jdk.internal.classfile.impl.Util;

/**
 * Models a local variable load instruction in the {@code code} array of a
 * {@code Code} attribute.  Corresponding opcodes have a {@linkplain
 * Opcode#kind() kind} of {@link Opcode.Kind#LOAD}.  Delivered as a {@link
 * CodeElement} when traversing the elements of a {@link CodeModel}.
 * <p>
 * A local variable load instruction is composite:
 * {@snippet lang=text :
 * // @link substring="LoadInstruction" target="#of(TypeKind, int)" :
 * LoadInstruction(
 *     TypeKind typeKind, // @link substring="typeKind" target="#typeKind"
 *     int slot // @link substring="slot" target="#slot"
 * )
 * }
 * where {@code TypeKind} is {@linkplain TypeKind##computational-type
 * computational}, and {@code slot} is within {@code [0, 65535]}.
 *
 * @see Opcode.Kind#LOAD
 * @see CodeBuilder#loadLocal CodeBuilder::loadLocal
 * @since 24
 */
public sealed interface LoadInstruction extends Instruction
        permits AbstractInstruction.BoundLoadInstruction,
                AbstractInstruction.UnboundLoadInstruction {

    /**
     * {@return the local variable slot to load from}
     * The value is within {@code [0, 65535]}.
     */
    int slot();

    /**
     * {@return the {@linkplain TypeKind##computational-type computational type}
     * of the value to be loaded}
     */
    TypeKind typeKind();

    /**
     * {@return a local variable load instruction}
     * {@code kind} is {@linkplain TypeKind#asLoadable() converted} to its
     * computational type.
     * {@code slot} must be within {@code [0, 65535]}.
     *
     * @param kind the type of the value to be loaded
     * @param slot the local variable slot to load from
     * @throws IllegalArgumentException if {@code kind} is
     *         {@link TypeKind#VOID void} or {@code slot} is out of range
     */
    static LoadInstruction of(TypeKind kind, int slot) {
        var opcode = BytecodeHelpers.loadOpcode(kind, slot); // validates slot, trusted
        return new AbstractInstruction.UnboundLoadInstruction(opcode, slot);
    }

    /**
     * {@return a local variable load instruction}
     * <p>
     * The range of {@code slot} is restricted by the {@code op} and its
     * {@linkplain Opcode#sizeIfFixed() size}:
     * <ul>
     * <li>If {@code op} has size 1, {@code slot} must be exactly the slot value
     * implied by the opcode.
     * <li>If {@code op} has size 2, {@code slot} must be within {@code [0, 255]}.
     * <li>If {@code op} has size 4, {@code slot} must be within {@code [0, 65535]}.
     * </ul>
     *
     * @apiNote
     * The explicit {@code op} argument allows creating {@code wide} or
     * regular load instructions when the {@code slot} can be encoded
     * with more optimized load instructions.
     *
     * @param op the opcode for the specific type of load instruction,
     *           which must be of kind {@link Opcode.Kind#LOAD}
     * @param slot the local variable slot to load from
     * @throws IllegalArgumentException if the opcode kind is not
     *         {@link Opcode.Kind#LOAD} or {@code slot} is out of range
     */
    static LoadInstruction of(Opcode op, int slot) {
        Util.checkKind(op, Opcode.Kind.LOAD);
        BytecodeHelpers.validateSlot(op, slot, true);
        return new AbstractInstruction.UnboundLoadInstruction(op, slot);
    }
}
