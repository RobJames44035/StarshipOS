/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xalan.internal.xsltc.compiler;

import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeSetType;

/**
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Morten Jorgensen
 * @author Erwin Bolwidt <ejb@klomp.org>
 */
final class VariableRef extends VariableRefBase {

    public VariableRef(Variable variable) {
        super(variable);
    }

    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();

        // Fall-through for variables that are implemented as methods
        if (_type.implementedAsMethod()) return;

        final String name = _variable.getEscapedName();
        final String signature = _type.toSignature();

        if (_variable.isLocal()) {
            if (classGen.isExternal()) {
                Closure variableClosure = _closure;
                while (variableClosure != null) {
                    if (variableClosure.inInnerClass()) break;
                    variableClosure = variableClosure.getParentClosure();
                }

                if (variableClosure != null) {
                    il.append(ALOAD_0);
                    il.append(new GETFIELD(
                        cpg.addFieldref(variableClosure.getInnerClassName(),
                            name, signature)));
                }
                else {
                    il.append(_variable.loadInstruction());
                }
            }
            else {
                il.append(_variable.loadInstruction());
            }
        }
        else {
            final String className = classGen.getClassName();
            il.append(classGen.loadTranslet());
            if (classGen.isExternal()) {
                il.append(new CHECKCAST(cpg.addClass(className)));
            }
            il.append(new GETFIELD(cpg.addFieldref(className,name,signature)));
        }

        if (_variable.getType() instanceof NodeSetType) {
            // The method cloneIterator() also does resetting
            final int clone = cpg.addInterfaceMethodref(NODE_ITERATOR,
                                                       "cloneIterator",
                                                       "()" +
                                                        NODE_ITERATOR_SIG);
            il.append(new INVOKEINTERFACE(clone, 1));
        }
    }
}
