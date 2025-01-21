/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package parsers;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class Bug6573786ErrorHandler extends DefaultHandler {
    public boolean fail = false;

    public void fatalError(SAXParseException e) throws SAXException {
        System.out.println(e.getMessage());
        if (e.getMessage().indexOf("bad_value") < 0) {
            fail = true;
        }
    } // fatalError ()

    public void error(SAXParseException e) throws SAXException {
        System.out.println(e.getMessage());
    } // error ()

    public void warning(SAXParseException e) throws SAXException {
        System.out.println(e.getMessage());
    } // warning ()
}
