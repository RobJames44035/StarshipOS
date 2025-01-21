/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * LOR - Bitwise OR long
 *
 * <PRE>
 * Stack: ..., value1, value2 -&gt; ..., result
 * </PRE>
 */
public class LOR extends ArithmeticInstruction {

    public LOR() {
        super(com.sun.org.apache.bcel.internal.Const.LOR);
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
        v.visitLOR(this);
    }
}
