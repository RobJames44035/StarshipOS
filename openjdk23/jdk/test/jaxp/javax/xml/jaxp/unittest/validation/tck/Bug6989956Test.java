/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation.tck;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * @test
 * @bug 6989956
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.tck.Bug6989956Test
 * @summary Test Validation can process correctly that maxOccurs in Choice less than maxOccurs in Elements contained in the Choice.
 */
public class Bug6989956Test {
    static final String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

    @Test
    public void test() {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            schemaFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            Schema schema = schemaFactory.newSchema(new StreamSource(Bug6989956Test.class.getResourceAsStream("Bug6989956.xsd")));

            Validator validator = schema.newValidator();
            validator.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            validator.setErrorHandler(new ErrorHandler() {
                public void error(SAXParseException exception) throws SAXException {
                    exception.printStackTrace();
                }

                public void fatalError(SAXParseException exception) throws SAXException {
                    exception.printStackTrace();
                }

                public void warning(SAXParseException exception) throws SAXException {
                    exception.printStackTrace();
                }
            });

            validator.validate(new StreamSource(Bug6989956Test.class.getResourceAsStream("Bug6989956.xml")));

        } catch (SAXException e) {
            System.out.println(e.getMessage());
            // fail(e.getMessage());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            // fail(e.getMessage());
        }
    }

    @Test
    public void testInstance() throws ParserConfigurationException, SAXException, IOException {
        System.out.println(Bug6989956Test.class.getResource("Bug6989956.xsd").getPath());
        File schemaFile = new File(Bug6989956Test.class.getResource("Bug6989956.xsd").getPath());
        SAXParser parser = createParser(schemaFile);

        try {
            parser.parse(Bug6989956Test.class.getResource("Bug6989956.xml").getPath(), new DefaultHandler());
        } catch (SAXException e) {
            e.printStackTrace();
            Assert.fail("Fatal Error: " + strException(e));
        }

    }

    protected SAXParser createParser(File schema) throws ParserConfigurationException, SAXException {

        // create and initialize the parser
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        spf.setValidating(true);
        SAXParser parser = spf.newSAXParser();
        parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");

        // set schemaLocation if possible
        try {
            parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", schema);
        } catch (SAXNotRecognizedException e) {
            System.out.println("Warning: Property 'http://java.sun.com/xml/jaxp/properties/schemaSource' is not recognized.");
        } catch (SAXNotSupportedException e) {
            System.out.println("Warning: Property 'http://java.sun.com/xml/jaxp/properties/schemaSource' is not supported.");
        }

        return parser;
    }

    protected static String strException(Exception ex) {
        StringBuffer sb = new StringBuffer();

        while (ex != null) {
            if (ex instanceof SAXParseException) {
                SAXParseException e = (SAXParseException) ex;
                sb.append("" + e.getSystemId() + "(" + e.getLineNumber() + "," + e.getColumnNumber() + "): " + e.getMessage());
                ex = e.getException();
            } else {
                sb.append(ex);
                ex = null;
            }
        }
        return sb.toString();
    }

}
