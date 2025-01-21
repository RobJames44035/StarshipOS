/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.bcel.internal.generic;

import java.util.StringTokenizer;

import com.sun.org.apache.bcel.internal.Const;
import com.sun.org.apache.bcel.internal.classfile.Constant;
import com.sun.org.apache.bcel.internal.classfile.ConstantCP;
import com.sun.org.apache.bcel.internal.classfile.ConstantPool;
import com.sun.org.apache.bcel.internal.classfile.Utility;

/**
 * Super class for the INVOKExxx family of instructions.
 *
 * @LastModified: Feb 2023
 */
public abstract class InvokeInstruction extends FieldOrMethod implements ExceptionThrower, StackConsumer, StackProducer {

    /**
     * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.
     */
    InvokeInstruction() {
    }

    /**
     * @param index to constant pool
     */
    protected InvokeInstruction(final short opcode, final int index) {
        super(opcode, index);
    }

    /**
     * Also works for instructions whose stack effect depends on the constant pool entry they reference.
     *
     * @return Number of words consumed from stack by this instruction
     */
    @Override
    public int consumeStack(final ConstantPoolGen cpg) {
        int sum;
        if (super.getOpcode() == Const.INVOKESTATIC || super.getOpcode() == Const.INVOKEDYNAMIC) {
            sum = 0;
        } else {
            sum = 1; // this reference
        }

        final String signature = getSignature(cpg);
        sum += Type.getArgumentTypesSize(signature);
        return sum;
    }

    /**
     * @return argument types of referenced method.
     */
    public Type[] getArgumentTypes(final ConstantPoolGen cpg) {
        return Type.getArgumentTypes(getSignature(cpg));
    }

    /**
     * This overrides the deprecated version as we know here that the referenced class may legally be an array.
     *
     * @return name of the referenced class/interface
     * @throws IllegalArgumentException if the referenced class is an array (this should not happen)
     */
    @Override
    @Deprecated
    public String getClassName( final ConstantPoolGen cpg ) {
        final ConstantPool cp = cpg.getConstantPool();
        final ConstantCP cmr = (ConstantCP) cp.getConstant(super.getIndex());
        final String className = cp.getConstantString(cmr.getClassIndex(), Const.CONSTANT_Class);
        return Utility.pathToPackage(className);
    }

    /**
     * @return name of referenced method.
     */
    public String getMethodName(final ConstantPoolGen cpg) {
        return getName(cpg);
    }

    /**
     * @return return type of referenced method.
     */
    public Type getReturnType(final ConstantPoolGen cpg) {
        return Type.getReturnType(getSignature(cpg));
    }

    /**
     * @return return type of referenced method.
     */
    @Override
    public Type getType(final ConstantPoolGen cpg) {
        return getReturnType(cpg);
    }

    /**
     * Also works for instructions whose stack effect depends on the constant pool entry they reference.
     *
     * @return Number of words produced onto stack by this instruction
     */
    @Override
    public int produceStack(final ConstantPoolGen cpg) {
        final String signature = getSignature(cpg);
        return Type.getReturnTypeSize(signature);
    }

    /**
     * @return mnemonic for instruction with symbolic references resolved
     */
    @Override
    public String toString(final ConstantPool cp) {
        final Constant c = cp.getConstant(super.getIndex());
        final StringTokenizer tok = new StringTokenizer(cp.constantToString(c));

        final String opcodeName = Const.getOpcodeName(super.getOpcode());

        final StringBuilder sb = new StringBuilder(opcodeName);
        if (tok.hasMoreTokens()) {
            sb.append(" ");
            sb.append(Utility.packageToPath(tok.nextToken()));
            if (tok.hasMoreTokens()) {
                sb.append(tok.nextToken());
            }
        }

        return sb.toString();
    }

}
