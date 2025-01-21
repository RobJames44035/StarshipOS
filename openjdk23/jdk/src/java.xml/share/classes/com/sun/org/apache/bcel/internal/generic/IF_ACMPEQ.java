/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * IF_ACMPEQ - Branch if reference comparison succeeds
 *
 * <PRE>
 * Stack: ..., value1, value2 -&gt; ...
 * </PRE>
 */
public class IF_ACMPEQ extends IfInstruction {

    /**
     * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.
     */
    IF_ACMPEQ() {
    }

    public IF_ACMPEQ(final InstructionHandle target) {
        super(com.sun.org.apache.bcel.internal.Const.IF_ACMPEQ, target);
    }

    /**
     * Call corresponding visitor method(s). The order is: Call visitor methods of implemented interfaces first, then call
     * methods according to the class hierarchy in descending order, i.e., the most specific visitXXX() call comes last.
     *
     * @param v Visitor object
     */
    @Override
    public void accept(final Visitor v) {
        v.visitStackConsumer(this);
        v.visitBranchInstruction(this);
        v.visitIfInstruction(this);
        v.visitIF_ACMPEQ(this);
    }

    /**
     * @return negation of instruction
     */
    @Override
    public IfInstruction negate() {
        return new IF_ACMPNE(super.getTarget());
    }
}
