/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.jaxp.validation;

import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;
import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarDescription;
import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;

/**
 * <p>Implementation of Schema for W3C XML Schemas
 * which contains no schema components.</p>
 *
 * @author Michael Glavassevich, IBM
 */
final class EmptyXMLSchema extends AbstractXMLSchema implements XMLGrammarPool {

    /** Zero length grammar array. */
    private static final Grammar [] ZERO_LENGTH_GRAMMAR_ARRAY = new Grammar [0];

    public EmptyXMLSchema() {}

    /*
     * XMLGrammarPool methods
     */

    public Grammar[] retrieveInitialGrammarSet(String grammarType) {
        return ZERO_LENGTH_GRAMMAR_ARRAY;
    }

    public void cacheGrammars(String grammarType, Grammar[] grammars) {}

    public Grammar retrieveGrammar(XMLGrammarDescription desc) {
        return null;
    }

    public void lockPool() {}

    public void unlockPool() {}

    public void clear() {}

    /*
     * XSGrammarPoolContainer methods
     */

    public XMLGrammarPool getGrammarPool() {
        return this;
    }

    public boolean isFullyComposed() {
        return true;
    }

} // EmptyXMLSchema
