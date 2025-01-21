/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * D2L - Convert double to long
 *
 * <PRE>
 * Stack: ..., value.word1, value.word2 -&gt; ..., result.word1, result.word2
 * </PRE>
 */
public class D2L extends ConversionInstruction {

    /**
     * Convert double to long
     */
    public D2L() {
        super(com.sun.org.apache.bcel.internal.Const.D2L);
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
        v.visitD2L(this);
    }
}
