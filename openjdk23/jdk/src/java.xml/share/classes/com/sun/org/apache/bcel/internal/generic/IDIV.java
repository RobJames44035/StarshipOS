/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.bcel.internal.generic;

import com.sun.org.apache.bcel.internal.ExceptionConst;

/**
 * IDIV - Divide ints
 *
 * <PRE>
 * Stack: ..., value1, value2 -&gt; result
 * </PRE>
 * @LastModified: Jan 2020
 */
public class IDIV extends ArithmeticInstruction implements ExceptionThrower {

    /**
     * Divide ints
     */
    public IDIV() {
        super(com.sun.org.apache.bcel.internal.Const.IDIV);
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
        v.visitStackProducer(this);
        v.visitStackConsumer(this);
        v.visitArithmeticInstruction(this);
        v.visitIDIV(this);
    }

    /**
     * @return exceptions this instruction may cause
     */
    @Override
    public Class<?>[] getExceptions() {
        return new Class<?>[] {ExceptionConst.ARITHMETIC_EXCEPTION};
    }
}
