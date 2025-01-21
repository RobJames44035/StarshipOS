/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * Super class for the IFxxx family of instructions.
 */
public abstract class IfInstruction extends BranchInstruction implements StackConsumer {

    /**
     * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.
     */
    IfInstruction() {
    }

    /**
     * @param opcode opcode of instruction
     * @param target Target instruction to branch to
     */
    protected IfInstruction(final short opcode, final InstructionHandle target) {
        super(opcode, target);
    }

    /**
     * @return negation of instruction, e.g. IFEQ.negate() == IFNE
     */
    public abstract IfInstruction negate();
}
