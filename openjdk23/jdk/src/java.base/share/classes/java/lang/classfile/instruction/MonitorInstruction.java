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
import jdk.internal.classfile.impl.Util;

/**
 * Models a {@link Opcode#MONITORENTER monitorenter} or {@link Opcode#MONITOREXIT
 * monitorexit} instruction in the {@code code} array of a {@code Code} attribute.
 * Corresponding opcodes have a {@linkplain Opcode#kind() kind} of {@link
 * Opcode.Kind#MONITOR}.  Delivered as a {@link CodeElement} when traversing the
 * elements of a {@link CodeModel}.
 * <p>
 * A monitor instruction is composite:
 * {@snippet lang=text :
 * // @link substring="MonitorInstruction" target="#of(Opcode)" :
 * MonitorInstruction(Opcode opcode) // @link substring="opcode" target="#opcode"
 * }
 *
 * @since 24
 */
public sealed interface MonitorInstruction extends Instruction
        permits AbstractInstruction.UnboundMonitorInstruction {

    /**
     * {@return a monitor instruction}
     *
     * @param op the opcode for the specific type of monitor instruction,
     *           which must be of kind {@link Opcode.Kind#MONITOR}
     * @throws IllegalArgumentException if the opcode kind is not
     *         {@link Opcode.Kind#MONITOR}.
     */
    static MonitorInstruction of(Opcode op) {
        Util.checkKind(op, Opcode.Kind.MONITOR);
        return new AbstractInstruction.UnboundMonitorInstruction(op);
    }
}
