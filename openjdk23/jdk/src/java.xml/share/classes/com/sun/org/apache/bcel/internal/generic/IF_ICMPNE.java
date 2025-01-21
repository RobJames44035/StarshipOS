/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * IF_ICMPNE - Branch if int comparison doesn't succeed
 *
 * <PRE>
 * Stack: ..., value1, value2 -&gt; ...
 * </PRE>
 */
public class IF_ICMPNE extends IfInstruction {

    /**
     * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.
     */
    IF_ICMPNE() {
    }

    public IF_ICMPNE(final InstructionHandle target) {
        super(com.sun.org.apache.bcel.internal.Const.IF_ICMPNE, target);
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
        v.visitIF_ICMPNE(this);
    }

    /**
     * @return negation of instruction
     */
    @Override
    public IfInstruction negate() {
        return new IF_ICMPEQ(super.getTarget());
    }
}
