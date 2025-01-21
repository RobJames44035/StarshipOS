/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import java.util.List;

/**
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Morten Jorgensen
 * @LastModified: Oct 2017
 */
final class NameCall extends NameBase {

    /**
     * Handles calls with no parameter (current node is implicit parameter).
     */
    public NameCall(QName fname) {
        super(fname);
    }

    /**
     * Handles calls with one parameter (either node or node-set).
     */
    public NameCall(QName fname, List<Expression> arguments) {
        super(fname, arguments);
    }

    /**
     * Translate code that leaves a node's QName (as a String) on the stack
     */
    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();

        final int getName = cpg.addInterfaceMethodref(DOM_INTF,
                                                      GET_NODE_NAME,
                                                      GET_NODE_NAME_SIG);
        super.translate(classGen, methodGen);
        il.append(new INVOKEINTERFACE(getName, 2));
    }
}
