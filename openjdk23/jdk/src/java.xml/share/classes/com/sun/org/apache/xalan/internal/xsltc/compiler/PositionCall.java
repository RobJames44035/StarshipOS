/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xalan.internal.xsltc.compiler;

import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.ILOAD;
import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.CompareGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TestGenerator;

/**
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Morten Jorgensen
 */
final class PositionCall extends FunctionCall {

    public PositionCall(QName fname) {
        super(fname);
    }

    public boolean hasPositionCall() {
        return true;
    }

    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        final InstructionList il = methodGen.getInstructionList();

        if (methodGen instanceof CompareGenerator) {
            il.append(((CompareGenerator)methodGen).loadCurrentNode());
        }
        else if (methodGen instanceof TestGenerator) {
            il.append(new ILOAD(POSITION_INDEX));
        }
        else {
            final ConstantPoolGen cpg = classGen.getConstantPool();
            final int index = cpg.addInterfaceMethodref(NODE_ITERATOR,
                                                       "getPosition",
                                                       "()I");

            il.append(methodGen.loadIterator());
            il.append(new INVOKEINTERFACE(index,1));
        }
    }
}
