/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package parsers;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class MyErrorHandler extends DefaultHandler {

    public boolean errorOccured = false;

    public void error(SAXParseException e) throws SAXException {

        System.err.println("Error: " + "[[" + e.getPublicId() + "]" + "[" + e.getSystemId() + "]]" + "[[" + e.getLineNumber() + "]" + "[" + e.getColumnNumber()
                + "]] " + e);

        errorOccured = true;
    }

    public void fatalError(SAXParseException e) throws SAXException {

        System.err.println("Fatal Error: " + e);

        errorOccured = true;
    }

    public void warning(SAXParseException e) throws SAXException {

        System.err.println("Warning: " + e);

        errorOccured = true;
    }
}
