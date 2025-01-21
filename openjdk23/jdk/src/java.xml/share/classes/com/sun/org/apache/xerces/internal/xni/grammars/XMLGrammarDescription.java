/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.xni.grammars;

import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;

/**
 * <p> This interface describes basic attributes of XML grammars--their
 * physical location and their type. </p>
 *
 * @author Neil Graham, IBM
 */
public interface XMLGrammarDescription extends XMLResourceIdentifier {

    // initial set of grammar constants that some configurations will recognize;user
    // components which create and/or recognize other types of grammars may
    // certainly use their own constants in place of these (so long as
    // their Grammar objects implement this interface).

    /**
     * The grammar type constant for XML Schema grammars. When getGrammarType()
     * method returns this constant, the object should be an instance of
     * the XMLSchemaDescription interface.
     */
    public static final String XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

    /**
     * The grammar type constant for DTD grammars. When getGrammarType()
     * method returns this constant, the object should be an instance of
     * the XMLDTDDescription interface.
     */
    public static final String XML_DTD = "http://www.w3.org/TR/REC-xml";

    /**
     * Return the type of this grammar.
     *
     * @return  the type of this grammar
     */
    public String getGrammarType();

} // interface XMLGrammarDescription
