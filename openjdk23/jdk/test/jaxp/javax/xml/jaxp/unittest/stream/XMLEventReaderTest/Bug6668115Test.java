/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream.XMLEventReaderTest;

import java.io.File;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6668115
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.XMLEventReaderTest.Bug6668115Test
 * @summary Test XMLEventReader.getElementText() shall update last event even if no peek.
 */
public class Bug6668115Test {

    public java.io.File input;
    public final String filesDir = "./";
    protected XMLInputFactory inputFactory;
    protected XMLOutputFactory outputFactory;

    /**
     * The reason the following call sequence is a problem is that with a
     * peekevent, getElementText calls nextEvent which does properly update the
     * lastEvent
     */
    @Test
    public void testNextTag() {
        try {
            XMLEventReader er = getReader();
            er.nextTag();
            er.nextTag();

            System.out.println(er.getElementText());
            er.nextTag();
            System.out.println(er.getElementText());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testNextTagWPeek() {
        try {
            XMLEventReader er = getReader();
            er.nextTag();
            er.nextTag();

            er.peek();
            System.out.println(er.getElementText());
            er.nextTag();
            System.out.println(er.getElementText());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    private XMLEventReader getReader() throws Exception {
        inputFactory = XMLInputFactory.newInstance();
        input = new File(getClass().getResource("play2.xml").getFile());
        // Check if event reader returns the correct event
        XMLEventReader er = inputFactory.createXMLEventReader(inputFactory.createXMLStreamReader(new java.io.FileInputStream(input), "UTF-8"));
        return er;
    }

}
