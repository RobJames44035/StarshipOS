/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.util;

import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Wraps {@link XMLErrorHandler} and make it look like a SAX {@link ErrorHandler}.
 *
 * <p>
 * The derived class should override the {@link #getErrorHandler()} method
 * so that it will return the correct {@link XMLErrorHandler} instance.
 * This method will be called whenever an error/warning is found.
 *
 * <p>
 * Experience shows that it is better to store the actual
 * {@link XMLErrorHandler} in one place and looks up that variable,
 * rather than copying it into every component that needs an error handler
 * and update all of them whenever it is changed, IMO.
 *
 * @author Kohsuke Kawaguchi
 *
 */
public abstract class ErrorHandlerProxy implements ErrorHandler {

    public void error(SAXParseException e) throws SAXException {
        XMLErrorHandler eh = getErrorHandler();
        if (eh instanceof ErrorHandlerWrapper) {
            ((ErrorHandlerWrapper)eh).fErrorHandler.error(e);
        }
        else {
            eh.error("","",ErrorHandlerWrapper.createXMLParseException(e));
        }
        // if an XNIException is thrown, just let it go.
        // REVISIT: is this OK? or should we try to wrap it into SAXException?
    }

    public void fatalError(SAXParseException e) throws SAXException {
        XMLErrorHandler eh = getErrorHandler();
        if (eh instanceof ErrorHandlerWrapper) {
            ((ErrorHandlerWrapper)eh).fErrorHandler.fatalError(e);
        }
        else {
            eh.fatalError("","",ErrorHandlerWrapper.createXMLParseException(e));
        }
    }

    public void warning(SAXParseException e) throws SAXException {
        XMLErrorHandler eh = getErrorHandler();
        if (eh instanceof ErrorHandlerWrapper) {
            ((ErrorHandlerWrapper)eh).fErrorHandler.warning(e);
        }
        else {
            eh.warning("","",ErrorHandlerWrapper.createXMLParseException(e));
        }
    }

    protected abstract XMLErrorHandler getErrorHandler();
}
