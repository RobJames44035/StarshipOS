/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ObjectType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
import java.util.List;

/**
 * @author Santiago Pericas-Geertsen
 * @LastModified: Oct 2017
 */
final class CastCall extends FunctionCall {

    /**
     * Name of the class that is the target of the cast. Must be a
     * fully-qualified Java class Name.
     */
    private String _className;

    /**
     * A reference to the expression being casted.
     */
    private Expression _right;

    /**
     * Constructor.
     */
    public CastCall(QName fname, List<Expression> arguments) {
        super(fname, arguments);
    }

    /**
     * Type check the two parameters for this function
     */
    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        // Check that the function was passed exactly two arguments
        if (argumentCount() != 2) {
            throw new TypeCheckError(new ErrorMsg(ErrorMsg.ILLEGAL_ARG_ERR,
                                                  getName(), this));
        }

        // The first argument must be a literal String
        Expression exp = argument(0);
        if (exp instanceof LiteralExpr) {
            _className = ((LiteralExpr) exp).getValue();
            _type = Type.newObjectType(_className);
        }
        else {
            throw new TypeCheckError(new ErrorMsg(ErrorMsg.NEED_LITERAL_ERR,
                                                  getName(), this));
        }

         // Second argument must be of type reference or object
        _right = argument(1);
        Type tright = _right.typeCheck(stable);
        if (tright != Type.Reference &&
            tright instanceof ObjectType == false)
        {
            throw new TypeCheckError(new ErrorMsg(ErrorMsg.DATA_CONVERSION_ERR,
                                                  tright, _type, this));
        }

        return _type;
    }

    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();

        _right.translate(classGen, methodGen);
        il.append(new CHECKCAST(cpg.addClass(_className)));
    }
}
