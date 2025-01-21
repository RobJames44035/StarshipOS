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
 * GETFIELD - Fetch field from object
 *
 * <PRE>
 * Stack: ..., objectref -&gt; ..., value
 * </PRE>
 *
 * OR
 *
 * <PRE>
 * Stack: ..., objectref -&gt; ..., value.word1, value.word2
 * </PRE>
 */
public class GETFIELD extends FieldInstruction implements ExceptionThrower, StackConsumer, StackProducer {

    /**
     * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.
     */
    GETFIELD() {
    }

    public GETFIELD(final int index) {
        super(Const.GETFIELD, index);
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
        v.visitStackProducer(this);
        v.visitTypedInstruction(this);
        v.visitLoadClass(this);
        v.visitCPInstruction(this);
        v.visitFieldOrMethod(this);
        v.visitFieldInstruction(this);
        v.visitGETFIELD(this);
    }

    @Override
    public Class<?>[] getExceptions() {
        return ExceptionConst.createExceptions(ExceptionConst.EXCS.EXCS_FIELD_AND_METHOD_RESOLUTION, ExceptionConst.NULL_POINTER_EXCEPTION,
            ExceptionConst.INCOMPATIBLE_CLASS_CHANGE_ERROR);
    }

    @Override
    public int produceStack(final ConstantPoolGen cpg) {
        return getFieldSize(cpg);
    }
}
