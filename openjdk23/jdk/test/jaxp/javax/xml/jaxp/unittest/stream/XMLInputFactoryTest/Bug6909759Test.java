/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream.XMLInputFactoryTest;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6909759
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.XMLInputFactoryTest.Bug6909759Test
 * @summary Test createXMLStreamReader with StreamSource.
 */
public class Bug6909759Test {


    @Test
    public void testCreateXMLStreamReader() {

        try {
            StreamSource ss = new StreamSource(getClass().getResourceAsStream("play.xml"));
            XMLInputFactory xif = XMLInputFactory.newInstance();
            // File file = new File("./tests/XMLStreamReader/sgml.xml");
            // FileInputStream inputStream = new FileInputStream(file);
            XMLStreamReader xsr;
            xsr = xif.createXMLStreamReader(ss);

            while (xsr.hasNext()) {
                int eventType = xsr.next();
            }

        } catch (UnsupportedOperationException oe) {
            Assert.fail("StreamSource should be supported");
        } catch (XMLStreamException ex) {
            Assert.fail("fix the test");
        }
    }
}
