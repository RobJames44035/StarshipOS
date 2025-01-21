/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * LASTORE - Store into long array
 *
 * <PRE>
 * Stack: ..., arrayref, index, value.word1, value.word2 -&gt; ...
 * </PRE>
 */
public class LASTORE extends ArrayInstruction implements StackConsumer {

    /**
     * Store long into array
     */
    public LASTORE() {
        super(com.sun.org.apache.bcel.internal.Const.LASTORE);
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
        v.visitLASTORE(this);
    }
}
