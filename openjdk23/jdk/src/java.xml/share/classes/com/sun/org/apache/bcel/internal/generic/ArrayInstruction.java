/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

import com.sun.org.apache.bcel.internal.ExceptionConst;

/**
 * Super class for instructions dealing with array access such as IALOAD.
 */
public abstract class ArrayInstruction extends Instruction implements ExceptionThrower, TypedInstruction {

    /**
     * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.
     */
    ArrayInstruction() {
    }

    /**
     * @param opcode of instruction
     */
    protected ArrayInstruction(final short opcode) {
        super(opcode, (short) 1);
    }

    @Override
    public Class<?>[] getExceptions() {
        return ExceptionConst.createExceptions(ExceptionConst.EXCS.EXCS_ARRAY_EXCEPTION);
    }

    /**
     * @return type associated with the instruction
     */
    @Override
    public Type getType(final ConstantPoolGen cp) {
        final short opcode = super.getOpcode();
        switch (opcode) {
        case com.sun.org.apache.bcel.internal.Const.IALOAD:
        case com.sun.org.apache.bcel.internal.Const.IASTORE:
            return Type.INT;
        case com.sun.org.apache.bcel.internal.Const.CALOAD:
        case com.sun.org.apache.bcel.internal.Const.CASTORE:
            return Type.CHAR;
        case com.sun.org.apache.bcel.internal.Const.BALOAD:
        case com.sun.org.apache.bcel.internal.Const.BASTORE:
            return Type.BYTE;
        case com.sun.org.apache.bcel.internal.Const.SALOAD:
        case com.sun.org.apache.bcel.internal.Const.SASTORE:
            return Type.SHORT;
        case com.sun.org.apache.bcel.internal.Const.LALOAD:
        case com.sun.org.apache.bcel.internal.Const.LASTORE:
            return Type.LONG;
        case com.sun.org.apache.bcel.internal.Const.DALOAD:
        case com.sun.org.apache.bcel.internal.Const.DASTORE:
            return Type.DOUBLE;
        case com.sun.org.apache.bcel.internal.Const.FALOAD:
        case com.sun.org.apache.bcel.internal.Const.FASTORE:
            return Type.FLOAT;
        case com.sun.org.apache.bcel.internal.Const.AALOAD:
        case com.sun.org.apache.bcel.internal.Const.AASTORE:
            return Type.OBJECT;
        default:
            throw new ClassGenException("Unknown case in switch" + opcode);
        }
    }
}
