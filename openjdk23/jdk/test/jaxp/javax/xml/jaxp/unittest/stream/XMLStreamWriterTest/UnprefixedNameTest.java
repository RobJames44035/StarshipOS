/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream.XMLStreamWriterTest;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6394074
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.XMLStreamWriterTest.UnprefixedNameTest
 * @summary Test XMLStreamWriter namespace prefix with writeDefaultNamespace.
 */
public class UnprefixedNameTest {

    @Test
    public void testUnboundPrefix() throws Exception {

        try {
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            XMLStreamWriter w = xof.createXMLStreamWriter(System.out);
            // here I'm trying to write
            // <bar xmlns="foo" />
            w.writeStartDocument();
            w.writeStartElement("foo", "bar");
            w.writeDefaultNamespace("foo");
            w.writeCharacters("---");
            w.writeEndElement();
            w.writeEndDocument();
            w.close();

            // Unexpected success
            String FAIL_MSG = "Unexpected success.  Expected: " + "XMLStreamException - " + "if the namespace URI has not been bound to a prefix "
                    + "and javax.xml.stream.isPrefixDefaulting has not been " + "set to true";
            System.err.println(FAIL_MSG);
            Assert.fail(FAIL_MSG);
        } catch (XMLStreamException xmlStreamException) {
            // Expected Exception
            System.out.println("Expected XMLStreamException: " + xmlStreamException.toString());
        }
    }

    @Test
    public void testBoundPrefix() throws Exception {

        try {
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            XMLStreamWriter w = xof.createXMLStreamWriter(System.out);
            // here I'm trying to write
            // <bar xmlns="foo" />
            w.writeStartDocument();
            w.writeStartElement("foo", "bar", "http://namespace");
            w.writeCharacters("---");
            w.writeEndElement();
            w.writeEndDocument();
            w.close();

            // Expected success
            System.out.println("Expected success.");
        } catch (Exception exception) {
            // Unexpected Exception
            String FAIL_MSG = "Unexpected Exception: " + exception.toString();
            System.err.println(FAIL_MSG);
            Assert.fail(FAIL_MSG);
        }
    }

    @Test
    public void testRepairingPrefix() throws Exception {

        try {

            // repair namespaces
            // use new XMLOutputFactory as changing its property settings
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            xof.setProperty(xof.IS_REPAIRING_NAMESPACES, new Boolean(true));
            XMLStreamWriter w = xof.createXMLStreamWriter(System.out);

            // here I'm trying to write
            // <bar xmlns="foo" />
            w.writeStartDocument();
            w.writeStartElement("foo", "bar");
            w.writeDefaultNamespace("foo");
            w.writeCharacters("---");
            w.writeEndElement();
            w.writeEndDocument();
            w.close();

            // Expected success
            System.out.println("Expected success.");
        } catch (Exception exception) {
            // Unexpected Exception
            String FAIL_MSG = "Unexpected Exception: " + exception.toString();
            System.err.println(FAIL_MSG);
            Assert.fail(FAIL_MSG);
        }
    }
}
