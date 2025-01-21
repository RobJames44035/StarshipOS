/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream.XMLOutputFactoryTest;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.sax.SAXResult;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.helpers.DefaultHandler;

/*
 * @test
 * @bug 6846132
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.XMLOutputFactoryTest.Bug6846132Test
 * @summary Test createXMLStreamWriter with SAXResult won't throw a NullPointerException.
 */
public class Bug6846132Test {

    @Test
    public void testSAXResult() {
        DefaultHandler handler = new DefaultHandler();

        final String EXPECTED_OUTPUT = "<?xml version=\"1.0\"?><root></root>";
        try {
            SAXResult saxResult = new SAXResult(handler);
            // saxResult.setSystemId("jaxp-ri/unit-test/javax/xml/stream/XMLOutputFactoryTest/cr6846132.xml");
            XMLOutputFactory ofac = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = ofac.createXMLStreamWriter(saxResult);
            writer.writeStartDocument("1.0");
            writer.writeStartElement("root");
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
            writer.close();
        } catch (Exception e) {
            if (e instanceof UnsupportedOperationException) {
                // expected
            } else {
                e.printStackTrace();
                Assert.fail(e.toString());
            }
        }
    }

    @Test
    public void testSAXResult1() {
        DefaultHandler handler = new DefaultHandler();

        try {
            SAXResult saxResult = new SAXResult(handler);
            XMLOutputFactory ofac = XMLOutputFactory.newInstance();
            XMLEventWriter writer = ofac.createXMLEventWriter(saxResult);
        } catch (Exception e) {
            if (e instanceof UnsupportedOperationException) {
                // expected
            } else {
                e.printStackTrace();
                Assert.fail(e.toString());
            }
        }
    }

}
