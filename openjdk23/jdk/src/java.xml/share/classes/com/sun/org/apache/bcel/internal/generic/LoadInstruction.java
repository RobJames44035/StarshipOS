/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * Denotes an unparameterized instruction to load a value from a local variable, e.g. ILOAD.
 */
public abstract class LoadInstruction extends LocalVariableInstruction implements PushInstruction {

    /**
     * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise. tag and length are defined in
     * readInstruction and initFromFile, respectively.
     */
    LoadInstruction(final short canonTag, final short cTag) {
        super(canonTag, cTag);
    }

    /**
     * @param opcode Instruction opcode
     * @param cTag Instruction number for compact version, ALOAD_0, e.g.
     * @param n local variable index (unsigned short)
     */
    protected LoadInstruction(final short opcode, final short cTag, final int n) {
        super(opcode, cTag, n);
    }

    /**
     * Call corresponding visitor method(s). The order is: Call visitor methods of implemented interfaces first, then call
     * methods according to the class hierarchy in descending order, i.e., the most specific visitXXX() call comes last.
     *
     * @param v Visitor object
     */
    @Override
    public void accept(final Visitor v) {
        v.visitStackProducer(this);
        v.visitPushInstruction(this);
        v.visitTypedInstruction(this);
        v.visitLocalVariableInstruction(this);
        v.visitLoadInstruction(this);
    }
}
