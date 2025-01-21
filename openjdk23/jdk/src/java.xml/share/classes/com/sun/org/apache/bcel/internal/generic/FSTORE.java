/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * FSTORE - Store float into local variable
 *
 * <PRE>
 * Stack: ..., value -&gt; ...
 * </PRE>
 */
public class FSTORE extends StoreInstruction {

    /**
     * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.
     */
    FSTORE() {
        super(com.sun.org.apache.bcel.internal.Const.FSTORE, com.sun.org.apache.bcel.internal.Const.FSTORE_0);
    }

    /**
     * Store float into local variable
     *
     * @param n index of local variable
     */
    public FSTORE(final int n) {
        super(com.sun.org.apache.bcel.internal.Const.FSTORE, com.sun.org.apache.bcel.internal.Const.FSTORE_0, n);
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
        v.visitFSTORE(this);
    }
}
