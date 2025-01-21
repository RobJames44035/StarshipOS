/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * F2D - Convert float to double
 *
 * <PRE>
 * Stack: ..., value -&gt; ..., result.word1, result.word2
 * </PRE>
 */
public class F2D extends ConversionInstruction {

    /**
     * Convert float to double
     */
    public F2D() {
        super(com.sun.org.apache.bcel.internal.Const.F2D);
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
        v.visitF2D(this);
    }
}
