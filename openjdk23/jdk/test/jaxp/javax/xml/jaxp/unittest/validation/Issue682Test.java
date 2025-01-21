/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation;

import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/*
 * @test
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.Issue682Test
 * @summary Test comination of fields in <xsd:unique>, for https://issues.apache.org/jira/browse/XERCESJ-682.
 */
public class Issue682Test {
    static final String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

    File testFile = new File(getClass().getResource("Issue682.xsd").getFile());

    @Test
    public void test() {
        try {
            Schema schema = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema").newSchema(new StreamSource(testFile));
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            saxParserFactory.setNamespaceAware(true);
            saxParserFactory.setSchema(schema);
            // saxParserFactory.setFeature("http://java.sun.com/xml/schema/features/report-ignored-element-content-whitespace",
            // true);
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(new DefaultHandler());
            // InputStream input =
            // ClassLoader.getSystemClassLoader().getResourceAsStream("test/test.xml");
            InputStream input = getClass().getResourceAsStream("Issue682.xml");
            System.out.println("Parse InputStream:");
            xmlReader.parse(new InputSource(input));
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.toString());
        }

    }

}
