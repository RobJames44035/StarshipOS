/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package sbd.test;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * @test
 * @bug 8326915
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm sbd.test.ExternalRefTest
 * @summary Part of the Secure-By-Default (SBD) project. This test verifies issues
 * and error message improvements related to external references.
 */
public class ExternalRefTest {
    /**
     * @bug 8326915
     * Verifies that SAXParseException rather than NPE is thrown when a validating
     * parser is restricted from processing external references.
     * @throws Exception if the test fails
     */
    @Test
    public void testValidatingParser() throws Exception {
        Assert.assertThrows(SAXParseException.class, () -> validateWithParser());
    }

    private void validateWithParser() throws Exception {
            SAXParserFactory spf = SAXParserFactory.newInstance();

            spf.setNamespaceAware(true);
            spf.setValidating(true);

            SAXParser parser = spf.newSAXParser();
            parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                    "http://www.w3.org/2001/XMLSchema");

            parser.setProperty("jdk.xml.jdkcatalog.resolve", "strict");
            File xmlFile = new File(getClass().getResource("ExternalRefTest.xml").getPath());

            parser.parse(xmlFile, new DefaultHandler());
    }
}
