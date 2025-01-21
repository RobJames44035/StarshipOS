/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream.XMLStreamReaderTest;

import java.io.StringReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6631265
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.XMLStreamReaderTest.Issue47Test
 * @summary Test XMLStreamReader.standaloneSet() presents if input document has a value for "standalone" attribute in xml declaration.
 */
public class Issue47Test {

    @Test
    public void testStandaloneSet() {
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><prefix:root xmlns=\"\" xmlns:null=\"\"></prefix:root>";

        try {
            XMLInputFactory xif = XMLInputFactory.newInstance();
            XMLStreamReader r = xif.createXMLStreamReader(new StringReader(xml));
            Assert.assertTrue(!r.standaloneSet() && !r.isStandalone());

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occured: " + e.getMessage());
        }
    }

    @Test
    public void testStandaloneSet1() {
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><prefix:root xmlns=\"\" xmlns:null=\"\"></prefix:root>";

        try {
            XMLInputFactory xif = XMLInputFactory.newInstance();
            XMLStreamReader r = xif.createXMLStreamReader(new StringReader(xml));
            Assert.assertTrue(r.standaloneSet() && !r.isStandalone());

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occured: " + e.getMessage());
        }
    }

    @Test
    public void testStandaloneSet2() {
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><prefix:root xmlns=\"\" xmlns:null=\"\"></prefix:root>";

        try {
            XMLInputFactory xif = XMLInputFactory.newInstance();
            XMLStreamReader r = xif.createXMLStreamReader(new StringReader(xml));
            AssertJUnit.assertTrue(r.standaloneSet() && r.isStandalone());

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occured: " + e.getMessage());
        }
    }
}
