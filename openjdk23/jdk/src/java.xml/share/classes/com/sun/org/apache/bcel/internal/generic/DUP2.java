/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * DUP2 - Duplicate two top operand stack words
 *
 * <PRE>
 * Stack: ..., word2, word1 -&gt; ..., word2, word1, word2, word1
 * </PRE>
 */
public class DUP2 extends StackInstruction implements PushInstruction {

    public DUP2() {
        super(com.sun.org.apache.bcel.internal.Const.DUP2);
    }

    /**
     * Call corresponding visitor method(s). The order is: Call visitor methods of implemented interfaces first, then call
     * methods according to the class hierarchy in descending order, i.e., the most specific visitXXX() call comes last.
     *
     * @param v Visitor object
     */
    @Override
    public void accept(final Visitor v) {
        v.visitStackProducer(this);
        v.visitPushInstruction(this);
        v.visitStackInstruction(this);
        v.visitDUP2(this);
    }
}
