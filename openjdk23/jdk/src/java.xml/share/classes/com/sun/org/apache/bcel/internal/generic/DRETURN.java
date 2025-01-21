/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * DRETURN - Return double from method
 *
 * <PRE>
 * Stack: ..., value.word1, value.word2 -&gt; &lt;empty&gt;
 * </PRE>
 */
public class DRETURN extends ReturnInstruction {

    /**
     * Return double from method
     */
    public DRETURN() {
        super(com.sun.org.apache.bcel.internal.Const.DRETURN);
    }

    /**
     * Call corresponding visitor method(s). The order is: Call visitor methods of implemented interfaces first, then call
     * methods according to the class hierarchy in descending order, i.e., the most specific visitXXX() call comes last.
     *
     * @param v Visitor object
     */
    @Override
    public void accept(final Visitor v) {
        v.visitExceptionThrower(this);
        v.visitTypedInstruction(this);
        v.visitStackConsumer(this);
        v.visitReturnInstruction(this);
        v.visitDRETURN(this);
    }
}
