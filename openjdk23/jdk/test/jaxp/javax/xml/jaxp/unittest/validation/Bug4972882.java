/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import util.DraconianErrorHandler;

/*
 * @test
 * @bug 4972882
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.Bug4972882
 * @summary Test Validator throws Exception when two identity-constraints are defined with the same {name} and {target namespace}.
 */
public class Bug4972882 {

    static final String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

    @Test
    public void test1() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setValidating(true);
        dbf.setAttribute(SCHEMA_LANGUAGE, XMLConstants.W3C_XML_SCHEMA_NS_URI);
        dbf.setAttribute(SCHEMA_SOURCE, Bug4972882.class.getResource("targetNS00101m2_stub.xsd").toExternalForm());

        DocumentBuilder builder = dbf.newDocumentBuilder();
        builder.setErrorHandler(new DraconianErrorHandler());

        try {
            builder.parse(Bug4972882.class.getResource("targetNS00101m2_stub.xml").toExternalForm());
            Assert.fail("failure expected");
        } catch (SAXException e) {
            Assert.assertTrue(e.getMessage().indexOf("sch-props-correct.2") != -1);
        }
    }
}
