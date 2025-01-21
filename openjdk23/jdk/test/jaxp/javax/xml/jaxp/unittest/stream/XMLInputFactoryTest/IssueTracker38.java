/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream.XMLInputFactoryTest;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.XMLInputFactoryTest.IssueTracker38
 * @summary Test createXMLEventReader from DOM or SAX source is unsupported.
 */
public class IssueTracker38 {

    @Test
    public void testXMLEventReaderFromDOMSource() throws Exception {
        try {
                createEventReaderFromSource(new DOMSource());
            Assert.fail("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
        }
    }

    @Test
    public void testXMLStreamReaderFromDOMSource() throws Exception {
        try {
                createStreamReaderFromSource(new DOMSource());
            Assert.fail("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException oe) {
        }
    }

    @Test
    public void testXMLEventReaderFromSAXSource() throws Exception {
        try {
                createEventReaderFromSource(new SAXSource());
            Assert.fail("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
        }
    }

    @Test
    public void testXMLStreamReaderFromSAXSource() throws Exception {
        try {
                createStreamReaderFromSource(new SAXSource());
            Assert.fail("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException oe) {
        }
    }

    private void createEventReaderFromSource(Source source) throws Exception {
        XMLInputFactory xIF = XMLInputFactory.newInstance();
        xIF.createXMLEventReader(source);
    }

    private void createStreamReaderFromSource(Source source) throws Exception {
        XMLInputFactory xIF = XMLInputFactory.newInstance();
        xIF.createXMLStreamReader(source);
    }


}
