/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation.tck;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

/*
 * @test
 * @bug 6964720
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.tck.Bug6964720Test
 * @summary Test Schema doesn't allow the inexpressible union of two attribute wildcards.
 */
public class Bug6964720Test {
    static final String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

    @Test
    public void test() {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new StreamSource(Bug6964720Test.class.getResourceAsStream("Bug6964720.xsd")));
            Assert.fail("should produce an error message");
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        }
    }

}
