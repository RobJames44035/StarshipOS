/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * ILOAD - Load int from local variable onto stack
 *
 * <PRE>
 * Stack: ... -&gt; ..., result
 * </PRE>
 */
public class ILOAD extends LoadInstruction {

    /**
     * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.
     */
    ILOAD() {
        super(com.sun.org.apache.bcel.internal.Const.ILOAD, com.sun.org.apache.bcel.internal.Const.ILOAD_0);
    }

    /**
     * Load int from local variable
     *
     * @param n index of local variable
     */
    public ILOAD(final int n) {
        super(com.sun.org.apache.bcel.internal.Const.ILOAD, com.sun.org.apache.bcel.internal.Const.ILOAD_0, n);
    }

    /**
     * Call corresponding visitor method(s). The order is: Call visitor methods of implemented interfaces first, then call
     * methods according to the class hierarchy in descending order, i.e., the most specific visitXXX() call comes last.
     *
     * @param v Visitor object
     */
    @Override
    public void accept(final Visitor v) {
        super.accept(v);
        v.visitILOAD(this);
    }
}
