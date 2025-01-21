/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/*
 * @test
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.OccursTest
 * @summary Test Schema Validator can parse multiple or unbounded occurs.
 */
public class OccursTest {

    boolean errorFound;

    DocumentBuilder documentBuilder;

    private void printMethodName() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        System.out.println(stack[2].getMethodName());
    }

    public OccursTest() throws Exception {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new File(getClass().getResource("occurs.xsd").getFile()));

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setSchema(schema);

        documentBuilder = dbf.newDocumentBuilder();
        documentBuilder.setErrorHandler(new ErrorHandler() {
            public void error(SAXParseException e) throws SAXException {
                System.out.println("Error: " + e.getMessage());
                errorFound = true;
            }

            public void fatalError(SAXParseException e) throws SAXException {
                System.out.println("Fatal error: " + e.getMessage());
            }

            public void warning(SAXParseException e) throws SAXException {
                System.out.println("Warning: " + e.getMessage());
            }
        });
    }

    @Test
    public void testOccursMaxMinOk() throws Exception {
        printMethodName();

        File xmlFile = new File(getClass().getResource("occurs-ok.xml").getFile());
        try {
            errorFound = false;
            documentBuilder.parse(xmlFile);
        } catch (SAXException ex) {
            Assert.fail(ex.getMessage());
        }
        if (errorFound) {
            Assert.fail("Unexpected validation error reported");
        }
    }

    @Test
    public void testOccursMaxMinErrorMin() throws Exception {
        printMethodName();

        File xmlFile = new File(getClass().getResource("occurs-error-min.xml").getFile());
        try {
            errorFound = false;
            documentBuilder.parse(xmlFile);
        } catch (SAXException ex) {
            Assert.fail(ex.getMessage());
        }
        if (!errorFound) {
            Assert.fail("Expected validation error not reported");
        }
    }

    @Test
    public void testOccursMaxMinErrorMax() throws Exception {
        printMethodName();

        File xmlFile = new File(getClass().getResource("occurs-error-max.xml").getFile());
        try {
            errorFound = false;
            documentBuilder.parse(xmlFile);
        } catch (SAXException ex) {
            Assert.fail(ex.getMessage());
        }
        if (!errorFound) {
            Assert.fail("Expected validation error not reported");
        }
    }

    @Test
    public void testOccursMaxMinErrorMinMax() throws Exception {
        printMethodName();

        File xmlFile = new File(getClass().getResource("occurs-error-min-max.xml").getFile());
        try {
            errorFound = false;
            documentBuilder.parse(xmlFile);
        } catch (SAXException ex) {
            Assert.fail(ex.getMessage());
        }
        if (!errorFound) {
            Assert.fail("Expected validation error not reported");
        }
    }

}
