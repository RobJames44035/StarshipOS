/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.parsers;

import com.sun.org.apache.xerces.internal.impl.Constants;
import com.sun.org.apache.xerces.internal.util.SymbolTable;
import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;
import com.sun.org.apache.xerces.internal.xni.parser.XMLParserConfiguration;

/**
 * This is a concrete vanilla XML parser class. It uses the abstract parser
 * with either a BasicConfiguration object or the one specified by the
 * application.
 *
 * @author Arnaud  Le Hors, IBM
 * @author Andy Clark, IBM
 *
 */
public class XMLDocumentParser
    extends AbstractXMLDocumentParser {

    //
    // Constructors
    //

    /**
     * Constructs a document parser using the default basic parser
     * configuration.
     */
    public XMLDocumentParser() {
        super(new XIncludeAwareParserConfiguration());
    } // <init>()

    /**
     * Constructs a document parser using the specified parser configuration.
     */
    public XMLDocumentParser(XMLParserConfiguration config) {
        super(config);
    } // <init>(ParserConfiguration)

    /**
     * Constructs a document parser using the specified symbol table.
     */
    public XMLDocumentParser(SymbolTable symbolTable) {
        super(new XIncludeAwareParserConfiguration());
        fConfiguration.setProperty(Constants.XERCES_PROPERTY_PREFIX+Constants.SYMBOL_TABLE_PROPERTY, symbolTable);
    } // <init>(SymbolTable)

    /**
     * Constructs a document parser using the specified symbol table and
     * grammar pool.
     */
    public XMLDocumentParser(SymbolTable symbolTable,
                             XMLGrammarPool grammarPool) {
        super(new XIncludeAwareParserConfiguration());
        fConfiguration.setProperty(Constants.XERCES_PROPERTY_PREFIX+Constants.SYMBOL_TABLE_PROPERTY, symbolTable);
        fConfiguration.setProperty(Constants.XERCES_PROPERTY_PREFIX+Constants.XMLGRAMMAR_POOL_PROPERTY, grammarPool);
    }

} // class XMLDocumentParser
