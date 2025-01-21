/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation;

import java.net.URL;

import javax.xml.validation.SchemaFactory;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * @test
 * @bug 4996446
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.Bug4996446
 * @summary Test SchemaFactory can detect violations of the "Schema Component Constraint: Element Declarations Consistent".
 */
public class Bug4996446 {

    SchemaFactory schemaFactory = null;

    @BeforeMethod
    public void setUp() {
        schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
    }

    @AfterMethod
    public void tearDown() {
        schemaFactory = null;
    }

    @Test
    public void testOne() {

        ErrorHandler errorHandler = new ErrorHandler();
        schemaFactory.setErrorHandler(errorHandler);
        URL fileName = Bug4996446.class.getResource("Bug4996446.xsd");
        try {
            schemaFactory.newSchema(fileName);
        } catch (SAXException e) {
        }

        if (errorHandler.errorCounter == 0) {
            Assert.fail(" No Errors reported: " + errorHandler.errorCounter);
        }
        return;
    }
}

class ErrorHandler extends DefaultHandler {
    public int errorCounter = 0;

    public void error(SAXParseException e) throws SAXException {
        // System.out.println(e);
        errorCounter++;
    }

    public void fatalError(SAXParseException e) throws SAXException {
        // System.out.println(e);
        errorCounter++;
    }
}
