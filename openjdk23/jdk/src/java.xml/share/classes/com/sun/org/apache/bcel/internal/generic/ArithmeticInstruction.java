/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

import com.sun.org.apache.bcel.internal.Const;

/**
 * Super class for the family of arithmetic instructions.
 */
public abstract class ArithmeticInstruction extends Instruction implements TypedInstruction, StackProducer, StackConsumer {

    /**
     * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.
     */
    ArithmeticInstruction() {
    }

    /**
     * @param opcode of instruction
     */
    protected ArithmeticInstruction(final short opcode) {
        super(opcode, (short) 1);
    }

    /**
     * @return type associated with the instruction
     */
    @Override
    public Type getType(final ConstantPoolGen cp) {
        final short opcode = super.getOpcode();
        switch (opcode) {
        case Const.DADD:
        case Const.DDIV:
        case Const.DMUL:
        case Const.DNEG:
        case Const.DREM:
        case Const.DSUB:
            return Type.DOUBLE;
        case Const.FADD:
        case Const.FDIV:
        case Const.FMUL:
        case Const.FNEG:
        case Const.FREM:
        case Const.FSUB:
            return Type.FLOAT;
        case Const.IADD:
        case Const.IAND:
        case Const.IDIV:
        case Const.IMUL:
        case Const.INEG:
        case Const.IOR:
        case Const.IREM:
        case Const.ISHL:
        case Const.ISHR:
        case Const.ISUB:
        case Const.IUSHR:
        case Const.IXOR:
            return Type.INT;
        case Const.LADD:
        case Const.LAND:
        case Const.LDIV:
        case Const.LMUL:
        case Const.LNEG:
        case Const.LOR:
        case Const.LREM:
        case Const.LSHL:
        case Const.LSHR:
        case Const.LSUB:
        case Const.LUSHR:
        case Const.LXOR:
            return Type.LONG;
        default: // Never reached
            throw new ClassGenException("Unknown type " + opcode);
        }
    }
}
