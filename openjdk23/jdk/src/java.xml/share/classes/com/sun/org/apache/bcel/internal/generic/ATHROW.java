/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.bcel.internal.generic;

import com.sun.org.apache.bcel.internal.ExceptionConst;

/**
 * ATHROW - Throw exception
 *
 * <PRE>
 * Stack: ..., objectref -&gt; objectref
 * </PRE>
 */
public class ATHROW extends Instruction implements UnconditionalBranch, ExceptionThrower {

    /**
     * Throw exception
     */
    public ATHROW() {
        super(com.sun.org.apache.bcel.internal.Const.ATHROW, (short) 1);
    }

    /**
     * Call corresponding visitor method(s). The order is: Call visitor methods of implemented interfaces first, then call
     * methods according to the class hierarchy in descending order, i.e., the most specific visitXXX() call comes last.
     *
     * @param v Visitor object
     */
    @Override
    public void accept(final Visitor v) {
        v.visitUnconditionalBranch(this);
        v.visitExceptionThrower(this);
        v.visitATHROW(this);
    }

    /**
     * @return exceptions this instruction may cause
     */
    @Override
    public Class<?>[] getExceptions() {
        return new Class<?>[] {ExceptionConst.THROWABLE};
    }
}
