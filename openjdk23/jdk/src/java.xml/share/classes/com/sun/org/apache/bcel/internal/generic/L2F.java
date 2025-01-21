/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * L2F - Convert long to float
 *
 * <PRE>
 * Stack: ..., value.word1, value.word2 -&gt; ..., result
 * </PRE>
 */
public class L2F extends ConversionInstruction {

    public L2F() {
        super(com.sun.org.apache.bcel.internal.Const.L2F);
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
        v.visitConversionInstruction(this);
        v.visitL2F(this);
    }
}
