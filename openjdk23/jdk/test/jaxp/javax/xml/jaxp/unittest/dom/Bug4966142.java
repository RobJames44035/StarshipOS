/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package dom;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.TypeInfo;

/*
 * @test
 * @bug 4966142
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm dom.Bug4966142
 * @summary Test TypeInfo.isDerivedFrom(...) works instead of throws UnsupportedOperationException when the TypeInfo instance refers to a simple type.
 */
public class Bug4966142 {

    static final String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

    @Test
    public void test1() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setValidating(true);
        dbf.setAttribute(SCHEMA_LANGUAGE, XMLConstants.W3C_XML_SCHEMA_NS_URI);
        dbf.setAttribute(SCHEMA_SOURCE, Bug4966142.class.getResource("Bug4966142.xsd").toExternalForm());

        Document document = dbf.newDocumentBuilder().parse(Bug4966142.class.getResource("Bug4966142.xml").toExternalForm());

        TypeInfo type = document.getDocumentElement().getSchemaTypeInfo();

        Assert.assertFalse(type.isDerivedFrom("testNS", "Test", TypeInfo.DERIVATION_UNION));
    }
}
