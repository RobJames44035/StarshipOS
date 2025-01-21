/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xalan.internal.xsltc.compiler;

import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.GOTO;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;

/**
 * This class implements inlined calls to the XSLT standard functions
 * true() and false().
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
final class BooleanExpr extends Expression {
    private boolean _value;

    public BooleanExpr(boolean value) {
        _value = value;
    }

    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        _type = Type.Boolean;
        return _type;
    }

    public String toString() {
        return _value ? "true()" : "false()";
    }

    public boolean getValue() {
        return _value;
    }

    public boolean contextDependent() {
        return false;
    }

    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        ConstantPoolGen cpg = classGen.getConstantPool();
        InstructionList il = methodGen.getInstructionList();
        il.append(new PUSH(cpg, _value));
    }

    public void translateDesynthesized(ClassGenerator classGen,
                                       MethodGenerator methodGen) {
        final InstructionList il = methodGen.getInstructionList();
        if (_value) {
            il.append(NOP);     // true list falls through
        }
        else {
            _falseList.add(il.append(new GOTO(null)));
        }
    }
}
