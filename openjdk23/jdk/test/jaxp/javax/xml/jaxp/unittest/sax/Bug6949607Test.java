/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package sax;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * @test
 * @bug 6949607
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm sax.Bug6949607Test
 * @summary Test Attributes.getValue returns null when parameter uri is empty.
 */
public class Bug6949607Test {

    final String MSG = "Failed to parse XML";
    String textXML = "<prefix:rootElem xmlns:prefix=\"something\" prefix:attr=\"attrValue\" />";

    @Test
    public void testException() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(true);
            SAXParser saxParser = factory.newSAXParser();

            saxParser.parse(new ByteArrayInputStream(textXML.getBytes()), new TestFilter());

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    class TestFilter extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
            super.startElement(uri, localName, qName, atts);

            String attr_WithNs = atts.getValue("something", "attr");
            String attr_NoNs = atts.getValue("", "attr");

            System.out.println("withNs: " + attr_WithNs);
            System.out.println("NoNs: " + attr_NoNs);

            Assert.assertTrue(attr_NoNs == null, "Should return null when uri is empty.");

        }
    }

}
