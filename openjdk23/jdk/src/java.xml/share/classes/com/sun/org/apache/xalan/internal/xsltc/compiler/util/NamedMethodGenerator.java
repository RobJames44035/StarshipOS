/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xalan.internal.xsltc.compiler.util;

import com.sun.org.apache.bcel.internal.generic.ALOAD;
import com.sun.org.apache.bcel.internal.generic.ASTORE;
import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.Instruction;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.internal.generic.Type;

/**
 * This class is used for named templates. Named template methods have access
 * to the DOM, the current iterator, the handler and the current node.
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
public final class NamedMethodGenerator extends MethodGenerator {
    protected static final int CURRENT_INDEX  = 4;

    // The index of the first parameter (after dom/iterator/handler/current)
    private static final int PARAM_START_INDEX = 5;

    public NamedMethodGenerator(int access_flags, Type return_type,
                                Type[] arg_types, String[] arg_names,
                                String method_name, String class_name,
                                InstructionList il, ConstantPoolGen cp) {
        super(access_flags, return_type, arg_types, arg_names, method_name,
              class_name, il, cp);
    }

    public int getLocalIndex(String name) {
        if (name.equals("current")) {
            return CURRENT_INDEX;
        }
        return super.getLocalIndex(name);
    }

    public Instruction loadParameter(int index) {
        return new ALOAD(index + PARAM_START_INDEX);
    }

    public Instruction storeParameter(int index) {
        return new ASTORE(index + PARAM_START_INDEX);
    }
}
