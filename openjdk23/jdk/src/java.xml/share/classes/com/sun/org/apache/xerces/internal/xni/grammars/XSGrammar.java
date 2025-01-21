/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.xni.grammars;

import com.sun.org.apache.xerces.internal.xs.XSModel;

/**
 * Representing a schema grammar. It contains declaratoin/definitions from
 * a certain namespace. When a grammar is preparsed, and its grammar type is
 * XML Schema, it can be casted to this interface. Objects of this interface
 * can be converted to XSModel, from which further information about components
 * in this grammar can be obtained.
 *
 * @author Sandy Gao, IBM
 *
 */
public interface XSGrammar extends Grammar {

    /**
     * Return an <code>XSModel</code> that represents components in this schema
     * grammar and any schema grammars that are imported by this grammar
     * directly or indirectly.
     *
     * @return  an <code>XSModel</code> representing this schema grammar
     */
    public XSModel toXSModel();

    /**
     * Return an <code>XSModel</code> that represents components in this schema
     * grammar and the grammars in the <code>grammars</code>parameter,
     * any schema grammars that are imported by them directly or indirectly.
     *
     * @return  an <code>XSModel</code> representing these schema grammars
     */
    public XSModel toXSModel(XSGrammar[] grammars);

} // interface XSGrammar
