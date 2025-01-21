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
 * Super class for the x2y family of instructions.
 */
public abstract class ConversionInstruction extends Instruction implements TypedInstruction, StackProducer, StackConsumer {

    /**
     * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.
     */
    ConversionInstruction() {
    }

    /**
     * @param opcode opcode of instruction
     */
    protected ConversionInstruction(final short opcode) {
        super(opcode, (short) 1);
    }

    /**
     * @return type associated with the instruction
     */
    @Override
    public Type getType(final ConstantPoolGen cp) {
        final short opcode = super.getOpcode();
        switch (opcode) {
        case Const.D2I:
        case Const.F2I:
        case Const.L2I:
            return Type.INT;
        case Const.D2F:
        case Const.I2F:
        case Const.L2F:
            return Type.FLOAT;
        case Const.D2L:
        case Const.F2L:
        case Const.I2L:
            return Type.LONG;
        case Const.F2D:
        case Const.I2D:
        case Const.L2D:
            return Type.DOUBLE;
        case Const.I2B:
            return Type.BYTE;
        case Const.I2C:
            return Type.CHAR;
        case Const.I2S:
            return Type.SHORT;
        default: // Never reached
            throw new ClassGenException("Unknown type " + opcode);
        }
    }
}
