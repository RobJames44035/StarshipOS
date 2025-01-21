/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * FRETURN - Return float from method
 *
 * <PRE>
 * Stack: ..., value -&gt; &lt;empty&gt;
 * </PRE>
 */
public class FRETURN extends ReturnInstruction {

    /**
     * Return float from method
     */
    public FRETURN() {
        super(com.sun.org.apache.bcel.internal.Const.FRETURN);
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
        v.visitFRETURN(this);
    }
}
