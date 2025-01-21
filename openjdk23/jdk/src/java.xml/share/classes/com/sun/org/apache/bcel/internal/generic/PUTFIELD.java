/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

import com.sun.org.apache.bcel.internal.Const;
import com.sun.org.apache.bcel.internal.ExceptionConst;

/**
 * PUTFIELD - Put field in object
 *
 * <PRE>
 * Stack: ..., objectref, value -&gt; ...
 * </PRE>
 *
 * OR
 *
 * <PRE>
 * Stack: ..., objectref, value.word1, value.word2 -&gt; ...
 * </PRE>
 */
public class PUTFIELD extends FieldInstruction implements PopInstruction, ExceptionThrower {

    /**
     * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.
     */
    PUTFIELD() {
    }

    public PUTFIELD(final int index) {
        super(Const.PUTFIELD, index);
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
        v.visitPopInstruction(this);
        v.visitTypedInstruction(this);
        v.visitLoadClass(this);
        v.visitCPInstruction(this);
        v.visitFieldOrMethod(this);
        v.visitFieldInstruction(this);
        v.visitPUTFIELD(this);
    }

    @Override
    public int consumeStack(final ConstantPoolGen cpg) {
        return getFieldSize(cpg) + 1;
    }

    @Override
    public Class<?>[] getExceptions() {
        return ExceptionConst.createExceptions(ExceptionConst.EXCS.EXCS_FIELD_AND_METHOD_RESOLUTION, ExceptionConst.NULL_POINTER_EXCEPTION,
            ExceptionConst.INCOMPATIBLE_CLASS_CHANGE_ERROR);
    }
}
