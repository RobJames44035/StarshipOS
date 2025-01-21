/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.xml.internal;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Implements an ErrorHandler that simply passes on the Exception.
 */
public class ErrorHandlerProxy implements ErrorHandler {

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        throw exception;
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        throw exception;
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        throw exception;
    }
}
