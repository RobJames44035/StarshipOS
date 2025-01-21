/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * IMUL - Multiply ints
 *
 * <PRE>
 * Stack: ..., value1, value2 -&gt; result
 * </PRE>
 */
public class IMUL extends ArithmeticInstruction {

    /**
     * Multiply ints
     */
    public IMUL() {
        super(com.sun.org.apache.bcel.internal.Const.IMUL);
    }

    /**
     * Call corresponding visitor method(s). The order is: Call visitor methods of implemented interfaces first, then call
     * methods according to the class hierarchy in descending order, i.e., the most specific visitXXX() call comes last.
     *
     * @param v Visitor object
     */
    @Override
    public void accept(final Visitor v) {
        v.visitTypedInstruction(this);
        v.visitStackProducer(this);
        v.visitStackConsumer(this);
        v.visitArithmeticInstruction(this);
        v.visitIMUL(this);
    }
}
