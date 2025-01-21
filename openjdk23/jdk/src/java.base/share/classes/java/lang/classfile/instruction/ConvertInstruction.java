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
 * Models a primitive conversion instruction in the {@code code} array of a
 * {@code Code} attribute, such as {@link Opcode#I2L i2l}.  Corresponding opcodes
 * have a {@linkplain Opcode#kind() kind} of {@link Opcode.Kind#CONVERT}.
 * Delivered as a {@link CodeElement} when traversing the elements of a {@link CodeModel}.
 * <p>
 * A primitive conversion instruction is composite:
 * {@snippet lang=text :
 * // @link substring="ConvertInstruction" target="#of(TypeKind, TypeKind)" :
 * ConvertInstruction(
 *     TypeKind fromType, // @link substring="fromType" target="#fromType"
 *     TypeKind toType // @link substring="toType" target="#toType"
 * )
 * }
 * where these conversions are valid:
 * <ul>
 * <li>Between {@code int}, {@code long}, {@code float}, and {@code double}, where
 * {@code fromType != toType};
 * <li>From {@code int} to {@code byte}, {@code char}, and {@code short}.
 * </ul>
 *
 * @see Opcode.Kind#CONVERT
 * @see CodeBuilder#conversion CodeBuilder::conversion
 * @since 24
 */
public sealed interface ConvertInstruction extends Instruction
        permits AbstractInstruction.UnboundConvertInstruction {
    /**
     * {@return the source type to convert from}
     */
    TypeKind fromType();

    /**
     * {@return the destination type to convert to}
     */
    TypeKind toType();

    /**
     * {@return a conversion instruction}  Valid conversions are:
     * <ul>
     * <li>Between {@code int}, {@code long}, {@code float}, and {@code double},
     * where {@code fromType != toType};
     * <li>From {@code int} to {@code byte}, {@code char}, and {@code short}.
     * </ul>
     *
     * @param fromType the type to convert from
     * @param toType the type to convert to
     * @throws IllegalArgumentException if this is not a valid conversion
     */
    static ConvertInstruction of(TypeKind fromType, TypeKind toType) {
        return of(BytecodeHelpers.convertOpcode(fromType, toType));
    }

    /**
     * {@return a conversion instruction}
     *
     * @param op the opcode for the specific type of conversion instruction,
     *           which must be of kind {@link Opcode.Kind#CONVERT}
     * @throws IllegalArgumentException if the opcode kind is not
     *         {@link Opcode.Kind#CONVERT}.
     */
    static ConvertInstruction of(Opcode op) {
        Util.checkKind(op, Opcode.Kind.CONVERT);
        return new AbstractInstruction.UnboundConvertInstruction(op);
    }
}
