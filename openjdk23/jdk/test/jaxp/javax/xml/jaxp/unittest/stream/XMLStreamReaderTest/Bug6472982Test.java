/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream.XMLStreamReaderTest;

import java.io.InputStream;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6472982
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.XMLStreamReaderTest.Bug6472982Test
 * @summary Test XMLStreamReader.getNamespaceContext().getPrefix("") won't throw IllegalArgumentException.
 */
public class Bug6472982Test {
    String namespaceURI = "foobar.com";
    String rootElement = "foo";
    String childElement = "foochild";
    String prefix = "a";

    @Test
    public void testNamespaceContext() {
        try {
            XMLInputFactory xif = XMLInputFactory.newInstance();
            xif.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.TRUE);
            InputStream is = new java.io.ByteArrayInputStream(getXML().getBytes());
            XMLStreamReader sr = xif.createXMLStreamReader(is);
            NamespaceContext context = sr.getNamespaceContext();
            Assert.assertTrue(context.getPrefix("") == null);

        } catch (IllegalArgumentException iae) {
            Assert.fail("NamespacePrefix#getPrefix() should not throw an IllegalArgumentException for empty uri. ");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    String getXML() {
        StringBuffer sbuffer = new StringBuffer();
        sbuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sbuffer.append("<" + rootElement + " xmlns:");
        sbuffer.append(prefix);
        sbuffer.append("=\"" + namespaceURI + "\">");
        sbuffer.append("<" + prefix + ":" + childElement + ">");
        sbuffer.append("blahblah");
        sbuffer.append("</" + prefix + ":" + childElement + ">");
        sbuffer.append("</" + rootElement + ">");
        // System.out.println("XML = " + sbuffer.toString()) ;
        return sbuffer.toString();
    }
}
