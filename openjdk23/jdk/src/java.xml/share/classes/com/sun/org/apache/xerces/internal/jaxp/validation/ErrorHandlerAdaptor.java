/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.jaxp.validation;

import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
import com.sun.org.apache.xerces.internal.xni.parser.XMLParseException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

/**
 * Receives errors through Xerces {@link XMLErrorHandler}
 * and pass them down to SAX {@link ErrorHandler}.
 *
 * @author
 *     Kohsuke Kawaguchi
 */
public abstract class ErrorHandlerAdaptor implements XMLErrorHandler
{
    /** set to true if there was any error. */
    private boolean hadError = false;

    /**
     * returns if there was an error since the last invocation of
     * the resetError method.
     */
    public boolean hadError() { return hadError; }
    /** resets the error flag. */
    public void reset() { hadError = false; }

    /**
     * Implemented by the derived class to return the actual
     * {@link ErrorHandler} to which errors are sent.
     *
     * @return always return non-null valid object.
     */
    protected abstract ErrorHandler getErrorHandler();

    public void fatalError( String domain, String key, XMLParseException e ) {
        try {
            hadError = true;
            getErrorHandler().fatalError( Util.toSAXParseException(e) );
        } catch( SAXException se ) {
            throw new WrappedSAXException(se);
        }
    }

    public void error( String domain, String key, XMLParseException e ) {
        try {
            hadError = true;
            getErrorHandler().error( Util.toSAXParseException(e) );
        } catch( SAXException se ) {
            throw new WrappedSAXException(se);
        }
    }

    public void warning( String domain, String key, XMLParseException e ) {
        try {
            getErrorHandler().warning( Util.toSAXParseException(e) );
        } catch( SAXException se ) {
            throw new WrappedSAXException(se);
        }
    }

}
