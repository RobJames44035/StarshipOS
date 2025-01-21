/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.xni.parser;

import com.sun.org.apache.xerces.internal.xni.XMLDTDContentModelHandler;

/**
 * Defines a DTD content model source. In other words, any object that
 * implements this interface is able to emit DTD content model "events"
 * to the registered DTD content model handler. These events could be
 * produced by parsing an XML document's internal or external subset,
 * could be generated from some other source, or could be created
 * programmatically. This interface does not say <em>how</em> the events
 * are created, only that the implementor is able to emit them.
 *
 * @author Andy Clark, IBM
 *
 */
public interface XMLDTDContentModelSource {

    //
    // XMLDTDContentModelSource methods
    //

    /** Sets the DTD content model handler. */
    public void setDTDContentModelHandler(XMLDTDContentModelHandler handler);

    /** Returns the DTD content model handler. */
    public XMLDTDContentModelHandler getDTDContentModelHandler( );

} // interface XMLDTDContentModelSource
