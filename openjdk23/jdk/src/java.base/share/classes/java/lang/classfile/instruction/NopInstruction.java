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
 * Models a {@link Opcode#NOP nop} instruction in the {@code code}
 * array of a {@code Code} attribute.  Delivered as a {@link CodeElement}
 * when traversing the elements of a {@link CodeModel}.
 * <p>
 * A no-op instruction has no visible state.
 *
 * @see CodeBuilder#nop CodeBuilder::nop
 * @jvms 6.5.nop <em>nop</em>
 * @since 24
 */
public sealed interface NopInstruction extends Instruction
        permits AbstractInstruction.UnboundNopInstruction {
    /**
     * {@return a no-op instruction}
     */
    static NopInstruction of() {
        return new AbstractInstruction.UnboundNopInstruction();
    }
}
