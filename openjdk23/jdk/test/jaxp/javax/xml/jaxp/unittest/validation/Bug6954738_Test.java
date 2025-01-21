/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation;

import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.testng.annotations.Test;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/*
 * @test
 * @bug 6954738
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.Bug6954738_Test
 * @summary Test Validator can process a XML document containing an element with 8000 characters.
 */
public class Bug6954738_Test {
    static final String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

    @Test
    public void test() {
        //if StackOverflowError is thrown, it shall escape from both the ErrorHandler and catch block
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            Schema schema = schemaFactory.newSchema(new StreamSource(Bug6954738_Test.class.getResourceAsStream("Bug6954738.xsd")));
            Validator validator = schema.newValidator();
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
            validator.validate(new StreamSource(Bug6954738_Test.class.getResourceAsStream("Bug6954738.xml")));

        } catch (SAXException e) {
            System.out.println(e.getMessage());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

}
