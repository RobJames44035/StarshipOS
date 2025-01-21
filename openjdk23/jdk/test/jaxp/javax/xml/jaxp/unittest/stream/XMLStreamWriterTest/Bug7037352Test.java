/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream.XMLStreamWriterTest;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stream.StreamResult;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 7037352
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.XMLStreamWriterTest.Bug7037352Test
 * @summary Test XMLStreamWriter.getNamespaceContext().getPrefix with XML_NS_URI and XMLNS_ATTRIBUTE_NS_URI.
 */
public class Bug7037352Test {

    @Test
    public void test() {
        try {
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            StreamResult sr = new StreamResult();
            XMLStreamWriter xsw = xof.createXMLStreamWriter(sr);
            NamespaceContext nc = xsw.getNamespaceContext();
            System.out.println(nc.getPrefix(XMLConstants.XML_NS_URI));
            System.out.println("  expected result: " + XMLConstants.XML_NS_PREFIX);
            System.out.println(nc.getPrefix(XMLConstants.XMLNS_ATTRIBUTE_NS_URI));
            System.out.println("  expected result: " + XMLConstants.XMLNS_ATTRIBUTE);

            Assert.assertTrue(nc.getPrefix(XMLConstants.XML_NS_URI) == XMLConstants.XML_NS_PREFIX);
            Assert.assertTrue(nc.getPrefix(XMLConstants.XMLNS_ATTRIBUTE_NS_URI) == XMLConstants.XMLNS_ATTRIBUTE);

        } catch (Throwable ex) {
            Assert.fail(ex.toString());
        }
    }

}
