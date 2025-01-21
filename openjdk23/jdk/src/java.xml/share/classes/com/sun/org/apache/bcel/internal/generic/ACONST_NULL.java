/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * ACONST_NULL - Push null reference
 *
 * <PRE>
 * Stack: ... -&gt; ..., null
 * </PRE>
 */
public class ACONST_NULL extends Instruction implements PushInstruction, TypedInstruction {

    /**
     * Push null reference
     */
    public ACONST_NULL() {
        super(com.sun.org.apache.bcel.internal.Const.ACONST_NULL, (short) 1);
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
        v.visitTypedInstruction(this);
        v.visitACONST_NULL(this);
    }

    /**
     * @return Type.NULL
     */
    @Override
    public Type getType(final ConstantPoolGen cp) {
        return Type.NULL;
    }
}
