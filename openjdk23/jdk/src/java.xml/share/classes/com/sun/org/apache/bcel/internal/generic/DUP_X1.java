/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * DUP_X1 - Duplicate top operand stack word and put two down
 *
 * <PRE>
 * Stack: ..., word2, word1 -&gt; ..., word1, word2, word1
 * </PRE>
 */
public class DUP_X1 extends StackInstruction {

    public DUP_X1() {
        super(com.sun.org.apache.bcel.internal.Const.DUP_X1);
    }

    /**
     * Call corresponding visitor method(s). The order is: Call visitor methods of implemented interfaces first, then call
     * methods according to the class hierarchy in descending order, i.e., the most specific visitXXX() call comes last.
     *
     * @param v Visitor object
     */
    @Override
    public void accept(final Visitor v) {
        v.visitStackInstruction(this);
        v.visitDUP_X1(this);
    }
}
