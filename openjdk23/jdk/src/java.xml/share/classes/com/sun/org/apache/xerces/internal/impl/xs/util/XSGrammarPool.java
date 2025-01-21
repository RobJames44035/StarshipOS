/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.xerces.internal.impl.xs.util;

import com.sun.org.apache.xerces.internal.impl.Constants;
import com.sun.org.apache.xerces.internal.impl.xs.SchemaGrammar;
import com.sun.org.apache.xerces.internal.impl.xs.XSModelImpl;
import com.sun.org.apache.xerces.internal.util.XMLGrammarPoolImpl;
import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;
import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarDescription;
import com.sun.org.apache.xerces.internal.xs.XSModel;
import java.util.ArrayList;
import java.util.List;


/**
 * Add a method that return an <code>XSModel</code> that represents components in
 * the schema grammars in this pool implementation.
 *
 * @xerces.internal
 *
 * @LastModified: Nov 2017
 */
public class XSGrammarPool extends XMLGrammarPoolImpl {

    /**
     * Return an <code>XSModel</code> that represents components in
     * the schema grammars in this pool implementation.
     *
     * @return  an <code>XSModel</code> representing this schema grammar
     */
    public XSModel toXSModel() {
        return toXSModel(Constants.SCHEMA_VERSION_1_0);
    }

    public XSModel toXSModel(short schemaVersion) {
        List<Grammar> list = new ArrayList<>();
        for (int i = 0; i < fGrammars.length; i++) {
            for (Entry entry = fGrammars[i] ; entry != null ; entry = entry.next) {
                if (entry.desc.getGrammarType().equals(XMLGrammarDescription.XML_SCHEMA)) {
                    list.add(entry.grammar);
                }
            }
        }
        int size = list.size();
        if (size == 0) {
            return toXSModel(new SchemaGrammar[0], schemaVersion);
        }
        SchemaGrammar[] gs = list.toArray(new SchemaGrammar[size]);
        return toXSModel(gs, schemaVersion);
    }

    protected XSModel toXSModel(SchemaGrammar[] grammars, short schemaVersion) {
        return new XSModelImpl(grammars, schemaVersion);
    }

} // class XSGrammarPool
