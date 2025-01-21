/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.bcel.internal.generic;

import com.sun.org.apache.bcel.internal.ExceptionConst;

/**
 * ARRAYLENGTH - Get length of array
 *
 * <PRE>
 * Stack: ..., arrayref -&gt; ..., length
 * </PRE>
 * @LastModified: Feb 2023
 */
public class ARRAYLENGTH extends Instruction implements ExceptionThrower, StackProducer, StackConsumer /* since 6.0 */ {

    /**
     * Get length of array
     */
    public ARRAYLENGTH() {
        super(com.sun.org.apache.bcel.internal.Const.ARRAYLENGTH, (short) 1);
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
        v.visitStackProducer(this);
        v.visitARRAYLENGTH(this);
    }

    /**
     * @return exceptions this instruction may cause
     */
    @Override
    public Class<?>[] getExceptions() {
        return new Class<?>[] {ExceptionConst.NULL_POINTER_EXCEPTION};
    }
}
