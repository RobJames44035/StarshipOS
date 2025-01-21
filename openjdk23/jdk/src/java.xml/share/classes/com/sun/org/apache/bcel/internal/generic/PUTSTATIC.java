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
 * PUTSTATIC - Put static field in class
 *
 * <PRE>
 * Stack: ..., value -&gt; ...
 * </PRE>
 *
 * OR
 *
 * <PRE>
 * Stack: ..., value.word1, value.word2 -&gt; ...
 * </PRE>
 */
public class PUTSTATIC extends FieldInstruction implements ExceptionThrower, PopInstruction {

    /**
     * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.
     */
    PUTSTATIC() {
    }

    public PUTSTATIC(final int index) {
        super(Const.PUTSTATIC, index);
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
        v.visitPUTSTATIC(this);
    }

    @Override
    public int consumeStack(final ConstantPoolGen cpg) {
        return getFieldSize(cpg);
    }

    @Override
    public Class<?>[] getExceptions() {
        return ExceptionConst.createExceptions(ExceptionConst.EXCS.EXCS_FIELD_AND_METHOD_RESOLUTION, ExceptionConst.INCOMPATIBLE_CLASS_CHANGE_ERROR);
    }
}
