/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package validation;

/*
 * @test
 * @bug 8080907
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.AnyElementTest
 * @summary Test processContents attribute of any element
 */
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import java.net.URISyntaxException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class AnyElementTest {
    @BeforeClass
    public void setup() throws URISyntaxException, SAXException {
        validator = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI).newSchema(new StreamSource(getUri("ProcessContents.xsd"))).newValidator();
    }

    /*
     * processContents attribute - Specifies how the XML processor should handle
     * validation against the elements specified by this any element. Can be set
     * to one of the following:
     * strict - the XML processor must obtain the schema for the required
     * namespaces and validate the elements (this is default)
     * lax - same as strict, but if the schema cannot be obtained, no errors
     * will occur
     * skip - The XML processor does not attempt to validate any elements from
     * the specified namespaces
     */
    @Test
    public void testProcessContents() throws Exception {
        validator.validate(new StreamSource(getUri("ProcessContents-ok.xml")));
    }

    /*
     * When processContents="lax", validation will be performed when the element
     * is declared in the schema.
     */
    @Test(expectedExceptions = SAXParseException.class)
    public void testProcessContentsLax() throws Exception {
        validator.validate(new StreamSource(getUri("ProcessContents-lax-error.xml")));
    }

    /*
     * Get the URI of the file, which is in the same path as this class
     */
    private String getUri(String fileName) throws URISyntaxException {
        return this.getClass().getResource(fileName).toURI().toASCIIString();
    }

    private Validator validator;
}
