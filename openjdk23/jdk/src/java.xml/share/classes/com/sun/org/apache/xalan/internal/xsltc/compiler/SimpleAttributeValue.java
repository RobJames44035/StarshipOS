/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xalan.internal.xsltc.compiler;

import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;

/**
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
final class SimpleAttributeValue extends AttributeValue {

    private String _value; // The attributes value (literate string).

    /**
     * Creates a new simple attribute value.
     * @param value the attribute value.
     */
    public SimpleAttributeValue(String value) {
        _value = value;
    }

    /**
     * Returns this attribute value's type (String).
     * @param stable The compiler/parser's symbol table
     */
    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        return _type = Type.String;
    }

    public String toString() {
        return _value;
    }

    protected boolean contextDependent() {
        return false;
    }

    /**
     * Translate this attribute value into JVM bytecodes that pushes the
     * attribute value onto the JVM's stack.
     * @param classGen BCEL Java class generator
     * @param methodGen BCEL Java method generator
     */
    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();
        il.append(new PUSH(cpg, _value));
    }
}
