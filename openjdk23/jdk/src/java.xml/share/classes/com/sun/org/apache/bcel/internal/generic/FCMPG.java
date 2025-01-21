/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * FCMPG - Compare floats: value1 &gt; value2
 *
 * <PRE>
 * Stack: ..., value1, value2 -&gt; ..., result
 * </PRE>
 */
public class FCMPG extends Instruction implements TypedInstruction, StackProducer, StackConsumer {

    public FCMPG() {
        super(com.sun.org.apache.bcel.internal.Const.FCMPG, (short) 1);
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
        v.visitFCMPG(this);
    }

    /**
     * @return Type.FLOAT
     */
    @Override
    public Type getType(final ConstantPoolGen cp) {
        return Type.FLOAT;
    }
}
