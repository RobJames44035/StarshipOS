/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xalan.internal.xsltc.compiler;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;

/**
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
final class NamespaceAlias extends TopLevelElement {

    private String sPrefix;
    private String rPrefix;

    /*
     * The namespace alias definitions given here have an impact only on
     * literal elements and literal attributes.
     */
    public void parseContents(Parser parser) {
        sPrefix = getAttribute("stylesheet-prefix");
        rPrefix = getAttribute("result-prefix");
        parser.getSymbolTable().addPrefixAlias(sPrefix,rPrefix);
    }

    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        return Type.Void;
    }

    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        // do nada
    }
}
