/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler.util;

import com.sun.org.apache.bcel.internal.generic.Instruction;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Constants;

/**
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @LastModified: Oct 2017
 */
public final class VoidType extends Type {
    protected VoidType() {}

    public String toString() {
        return "void";
    }

    public boolean identicalTo(Type other) {
        return this == other;
    }

    public String toSignature() {
        return "V";
    }

    public com.sun.org.apache.bcel.internal.generic.Type toJCType() {
        return null;    // should never be called
    }

    public Instruction POP() {
        return NOP;
    }

    /**
     * Translates a void into an object of internal type <code>type</code>.
     * This translation is needed when calling external functions
     * that return void.
     *
     * @see     com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type#translateTo
     */
    public void translateTo(ClassGenerator classGen, MethodGenerator methodGen,
                            Type type) {
        if (type == Type.String) {
            translateTo(classGen, methodGen, (StringType) type);
        }
        else {
            ErrorMsg err = new ErrorMsg(ErrorMsg.DATA_CONVERSION_ERR,
                                        toString(), type.toString());
            classGen.getParser().reportError(Constants.FATAL, err);
        }
    }

    /**
     * Translates a void into a string by pushing the empty string ''.
     *
     * @see     com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type#translateTo
     */
    public void translateTo(ClassGenerator classGen, MethodGenerator methodGen,
                            StringType type) {
        final InstructionList il = methodGen.getInstructionList();
        il.append(new PUSH(classGen.getConstantPool(), ""));
    }

    /**
     * Translates an external (primitive) Java type into a void.
     * Only an external "void" can be converted to this class.
     */
    public void translateFrom(ClassGenerator classGen, MethodGenerator methodGen,
                              Class<?> clazz) {
        if (!clazz.getName().equals("void")) {
            ErrorMsg err = new ErrorMsg(ErrorMsg.DATA_CONVERSION_ERR,
                                        toString(), clazz.getName());
            classGen.getParser().reportError(Constants.FATAL, err);
        }
    }
}
