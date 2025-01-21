/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */
package test.astro;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import static test.astro.AstroConstants.ASTROCAT;
import static test.astro.AstroConstants.JAXP_SCHEMA_LANGUAGE;
import static test.astro.AstroConstants.JAXP_SCHEMA_SOURCE;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * @test
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm test.astro.SchemaValidationTest
 * @summary test parser sets schema related properties to do validation
 */
public class SchemaValidationTest {
    /*
     * Only set the schemaLanguage, without setting schemaSource. It should
     * work.
     */
    @Test
    public void testSchemaValidation() throws Exception {
        SAXParser sp = getValidatingParser();
        sp.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA_NS_URI);
        sp.parse(new File(ASTROCAT), new DefaultHandler());
    }

    /*
     * Test SAXException shall be thrown if schemaSource is set but
     * schemaLanguage is not set.
     */
    @Test(expectedExceptions = SAXException.class)
    public void testSchemaValidationNeg() throws Exception {
        SAXParser sp = getValidatingParser();
        sp.setProperty(JAXP_SCHEMA_SOURCE, "catalog.xsd");
        sp.parse(new File(ASTROCAT), new DefaultHandler());
    }

    private SAXParser getValidatingParser() throws ParserConfigurationException, SAXException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        spf.setValidating(true);
        return spf.newSAXParser();
    }
}
