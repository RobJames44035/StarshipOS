/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation;

import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/*
 * @test
 * @bug 6695843
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.Bug6695843Test
 * @summary Test Validator should report accurate element type if there is a violation on a complexType with simpleContent that extends a base complexType.
 */
public class Bug6695843Test {
    static final String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

    @Test
    public void testValidateComplexTypeWithSimpleContent() throws IOException, ParserConfigurationException, SAXException {
        try {
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            dFactory.setNamespaceAware(true);

            DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
            Document document = dBuilder.parse(getClass().getResourceAsStream("Bug6695843.xsd"));
            DOMSource domSource = new DOMSource(document);

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(domSource);

            String xmlFileName = "Bug6695843.xml";
            Document document1 = dBuilder.parse(getClass().getResourceAsStream(xmlFileName));
            DOMSource domSource1 = new DOMSource(document1);

            Validator validator = schema.newValidator();
            validator.validate(domSource1);
        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(e.getMessage());
            if (msg.indexOf("maxLength '20' for type 'null'") > 0) {
                Assert.fail("vague error message");
            }
        }
    }

}
