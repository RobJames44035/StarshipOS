/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ErrorHandlerImpl implements ErrorHandler {
   /** Creates a new instance of ErrorHandlerImpl */
    public ErrorHandlerImpl() {
    }

    public void error(SAXParseException sAXParseException) throws SAXException {
        throw new SAXException(sAXParseException);
    }

    public void fatalError(SAXParseException sAXParseException) throws SAXException {
        throw new SAXException(sAXParseException);
    }

    public void warning(SAXParseException sAXParseException) throws SAXException {
        throw new SAXException(sAXParseException);
    }

}
