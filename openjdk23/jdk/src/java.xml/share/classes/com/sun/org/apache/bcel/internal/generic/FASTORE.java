/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * FASTORE - Store into float array
 *
 * <PRE>
 * Stack: ..., arrayref, index, value -&gt; ...
 * </PRE>
 */
public class FASTORE extends ArrayInstruction implements StackConsumer {

    /**
     * Store float into array
     */
    public FASTORE() {
        super(com.sun.org.apache.bcel.internal.Const.FASTORE);
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
        v.visitExceptionThrower(this);
        v.visitTypedInstruction(this);
        v.visitArrayInstruction(this);
        v.visitFASTORE(this);
    }
}
