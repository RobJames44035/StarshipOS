/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.parser;

import java.io.InputStream;

import org.w3c.dom.Document;

/**
 * A interface to allow pluggable ways of parsing an InputStream into a DOM Document.
 */
public interface XMLParser {

    /**
     * Parses a document from the input stream.
     * Caller is responsible for closing the stream.
     *
     * @param inputStream
     * @param disallowDocTypeDeclarations
     * @return {@link Document}
     * @throws XMLParserException
     */
    Document parse(InputStream inputStream, boolean disallowDocTypeDeclarations) throws XMLParserException;

}