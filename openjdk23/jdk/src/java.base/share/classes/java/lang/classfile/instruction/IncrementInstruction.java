/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.instruction;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.CodeElement;
import java.lang.classfile.CodeModel;
import java.lang.classfile.Instruction;
import java.lang.classfile.Opcode;

import jdk.internal.classfile.impl.AbstractInstruction;

/**
 * Models a local variable increment instruction in the {@code code} array of a
 * {@code Code} attribute.  Corresponding opcodes have a {@linkplain Opcode#kind()
 * kind} of {@link Opcode.Kind#INCREMENT}.  Delivered as a {@link CodeElement} when
 * traversing the elements of a {@link CodeModel}.
 * <p>
 * A local variable increment instruction is composite:
 * {@snippet lang=text :
 * // @link substring="IncrementInstruction" target="#of" :
 * IncrementInstruction(
 *     int slot, // @link substring="slot" target="#slot()"
 *     int constant // @link substring="constant" target="#constant()"
 * )
 * }
 * where
 * <ul>
 * <li>{@code slot} must be within {@code [0, 65535]}.
 * <li>{@code constant} must be within {@code [-32768, 32767]}.
 * </ul>
 *
 * @see Opcode.Kind#INCREMENT
 * @see CodeBuilder#iinc CodeBuilder::iinc
 * @jvms 6.5.iinc <em>iinc</em>
 * @since 24
 */
public sealed interface IncrementInstruction extends Instruction
        permits AbstractInstruction.BoundIncrementInstruction,
                AbstractInstruction.UnboundIncrementInstruction {
    /**
     * {@return the local variable slot to increment}
     */
    int slot();

    /**
     * {@return the value to increment by}
     */
    int constant();

    /**
     * {@return an increment instruction}
     * <ul>
     * <li>{@code slot} must be within {@code [0, 65535]}.
     * <li>{@code constant} must be within {@code [-32768, 32767]}.
     * </ul>
     *
     * @param slot the local variable slot to increment
     * @param constant the value to increment by
     * @throws IllegalArgumentException if {@code slot} or {@code constant} is out of range
     */
    static IncrementInstruction of(int slot, int constant) {
        return new AbstractInstruction.UnboundIncrementInstruction(slot, constant);
    }
}
