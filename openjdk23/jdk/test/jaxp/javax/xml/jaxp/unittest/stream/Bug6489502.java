/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package stream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6489502
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.Bug6489502
 * @summary Test XMLInputFactory works correctly in case it repeats to create reader.
 */
public class Bug6489502 {

    public java.io.File input;
    public final String filesDir = "./";
    protected XMLInputFactory inputFactory = XMLInputFactory.newInstance();
    protected XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

    private static String xml = "<?xml version=\"1.0\"?><PLAY><TITLE>The Tragedy of Hamlet, Prince of Denmark</TITLE></PLAY>";

    @Test
    public void testEventReader1() {
        try {
            // Check if event reader returns the correct event
            XMLEventReader e1 = inputFactory.createXMLEventReader(inputFactory.createXMLStreamReader(new java.io.StringReader(xml)));
            Assert.assertEquals(e1.peek().getEventType(), XMLStreamConstants.START_DOCUMENT);

            // Repeat same steps to test factory state
            XMLEventReader e2 = inputFactory.createXMLEventReader(inputFactory.createXMLStreamReader(new java.io.StringReader(xml)));
            Assert.assertEquals(e2.peek().getEventType(), XMLStreamConstants.START_DOCUMENT);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testEventReader2() {
        try {
            // Now advance underlying reader and then call peek on event reader
            XMLStreamReader s1 = inputFactory.createXMLStreamReader(new java.io.StringReader(xml));
            Assert.assertEquals(s1.getEventType(), XMLStreamConstants.START_DOCUMENT);
            s1.next();
            s1.next(); // advance to <TITLE>
            Assert.assertTrue(s1.getLocalName().equals("TITLE"));

            XMLEventReader e3 = inputFactory.createXMLEventReader(s1);
            Assert.assertEquals(e3.peek().getEventType(), XMLStreamConstants.START_ELEMENT);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
