/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.bcel.internal.generic;

import com.sun.org.apache.bcel.internal.ExceptionConst;

/**
 * MONITORENTER - Enter monitor for object
 *
 * <PRE>
 * Stack: ..., objectref -&gt; ...
 * </PRE>
 * @LastModified: Jan 2020
 */
public class MONITORENTER extends Instruction implements ExceptionThrower, StackConsumer {

    public MONITORENTER() {
        super(com.sun.org.apache.bcel.internal.Const.MONITORENTER, (short) 1);
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
        v.visitStackConsumer(this);
        v.visitMONITORENTER(this);
    }

    @Override
    public Class<?>[] getExceptions() {
        return new Class<?>[] {ExceptionConst.NULL_POINTER_EXCEPTION};
    }
}
