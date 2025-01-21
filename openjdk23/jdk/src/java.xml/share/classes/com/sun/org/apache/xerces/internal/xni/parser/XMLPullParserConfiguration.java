/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.xni.parser;

import java.io.IOException;

import com.sun.org.apache.xerces.internal.xni.XNIException;

/**
 * Represents a parser configuration that can be used as the
 * configuration for a "pull" parser. A pull parser allows the
 * application to drive the parser instead of having document
 * information events "pushed" to the registered handlers.
 * <p>
 * A pull parser using this type of configuration first calls
 * the <code>setInputSource</code> method. After the input
 * source is set, the pull parser repeatedly calls the
 * <code>parse(boolean):boolean</code> method. This method
 * returns a value of true if there is more to parse in the
 * document.
 * <p>
 * Calling the <code>parse(XMLInputSource)</code> is equivalent
 * to setting the input source and calling the
 * <code>parse(boolean):boolean</code> method with a "complete"
 * value of <code>true</code>.
 *
 * @author Andy Clark, IBM
 *
 */
public interface XMLPullParserConfiguration
    extends XMLParserConfiguration {

    //
    // XMLPullParserConfiguration methods
    //

    // parsing

    /**
     * Sets the input source for the document to parse.
     *
     * @param inputSource The document's input source.
     *
     * @exception XMLConfigurationException Thrown if there is a
     *                        configuration error when initializing the
     *                        parser.
     * @exception IOException Thrown on I/O error.
     *
     * @see #parse(boolean)
     */
    public void setInputSource(XMLInputSource inputSource)
        throws XMLConfigurationException, IOException;

    /**
     * Parses the document in a pull parsing fashion.
     *
     * @param complete True if the pull parser should parse the
     *                 remaining document completely.
     *
     * @return True if there is more document to parse.
     *
     * @exception XNIException Any XNI exception, possibly wrapping
     *                         another exception.
     * @exception IOException  An IO exception from the parser, possibly
     *                         from a byte stream or character stream
     *                         supplied by the parser.
     *
     * @see #setInputSource
     */
    public boolean parse(boolean complete) throws XNIException, IOException;

    /**
     * If the application decides to terminate parsing before the xml document
     * is fully parsed, the application should call this method to free any
     * resource allocated during parsing. For example, close all opened streams.
     */
    public void cleanup();

} // interface XMLPullParserConfiguration
