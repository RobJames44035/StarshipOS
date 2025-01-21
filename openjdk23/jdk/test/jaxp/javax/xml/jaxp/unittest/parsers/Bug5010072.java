/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package parsers;

import javax.xml.validation.SchemaFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * @test
 * @bug 5010072
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm parsers.Bug5010072
 * @summary Test SchemaFactory throws SAXException if xpath is "@".
 */
public class Bug5010072 {

    protected static class ErrorHandler extends DefaultHandler {
        public int errorCounter = 0;

        public void error(SAXParseException e) throws SAXException {

            System.err.println("Error: " + "[[" + e.getPublicId() + "][" + e.getSystemId() + "]]" + "[[" + e.getLineNumber() + "][" + e.getColumnNumber()
                    + "]]" + e);

            errorCounter++;

            throw e;
        }

        public void fatalError(SAXParseException e) throws SAXException {
            System.err.println("Fatal Error: " + e);
            errorCounter++;
        }
    }

    @Test
    public void test1() throws Exception {
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

        ErrorHandler errorHandler = new ErrorHandler();
        schemaFactory.setErrorHandler(errorHandler);

        try {
            schemaFactory.newSchema(Bug5010072.class.getResource("Bug5010072.xsd"));
            Assert.fail("should fail to compile");
        } catch (SAXException e) {
            ; // as expected
        }
    }
}
