/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
import com.sun.org.apache.bcel.internal.generic.INVOKESTATIC;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import java.util.List;

/**
 * @author Morten Jorgensen
 * @LastModified: Oct 2017
 */
final class LocalNameCall extends NameBase {

    /**
     * Handles calls with no parameter (current node is implicit parameter).
     */
    public LocalNameCall(QName fname) {
        super(fname);
    }

    /**
     * Handles calls with one parameter (either node or node-set).
     */
    public LocalNameCall(QName fname, List<Expression> arguments) {
        super(fname, arguments);
    }

    /**
     * This method is called when the constructor is compiled in
     * Stylesheet.compileConstructor() and not as the syntax tree is traversed.
     */
    public void translate(ClassGenerator classGen,
                          MethodGenerator methodGen) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();

        // Returns the name of a node in the DOM
        final int getNodeName = cpg.addInterfaceMethodref(DOM_INTF,
                                                          "getNodeName",
                                                          "(I)"+STRING_SIG);

        final int getLocalName = cpg.addMethodref(BASIS_LIBRARY_CLASS,
                                                  "getLocalName",
                                                  "(Ljava/lang/String;)"+
                                                  "Ljava/lang/String;");
        super.translate(classGen, methodGen);
        il.append(new INVOKEINTERFACE(getNodeName, 2));
        il.append(new INVOKESTATIC(getLocalName));
    }
}
