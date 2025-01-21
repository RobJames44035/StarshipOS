/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.xni.parser;

import com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler;

/**
 * Defines a document source. In other words, any object that implements
 * this interface is able to emit document "events" to the registered
 * document handler. These events could be produced by parsing an XML
 * document, could be generated from some other source, or could be
 * created programmatically. This interface does not say <em>how</em>
 * the events are created, only that the implementor is able to emit
 * them.
 *
 * @author Andy Clark, IBM
 *
 */
public interface XMLDocumentSource {

    //
    // XMLDocumentSource methods
    //

    /** Sets the document handler. */
    public void setDocumentHandler(XMLDocumentHandler handler);

    /** Returns the document handler */
    public XMLDocumentHandler getDocumentHandler();

} // interface XMLDocumentSource
