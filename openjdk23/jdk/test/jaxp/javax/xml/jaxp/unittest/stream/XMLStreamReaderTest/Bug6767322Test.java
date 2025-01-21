/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream.XMLStreamReaderTest;

import java.io.ByteArrayInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6767322
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.XMLStreamReaderTest.Bug6767322Test
 * @summary Test XMLStreamReader.getVersion() returns null if a version isn't declared.
 */
public class Bug6767322Test {
    private static final String INPUT_FILE = "Bug6767322.xml";

    @Test
    public void testVersionSet() {
        try {
            XMLStreamReader r = XMLInputFactory.newInstance().createXMLStreamReader(this.getClass().getResource(INPUT_FILE).toExternalForm(),
                    this.getClass().getResourceAsStream(INPUT_FILE));

            String version = r.getVersion();
            System.out.println("Bug6767322.xml: " + version);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occured: " + e.getMessage());
        }
    }

    @Test
    public void testVersionNotSet() {
        try {
            String xmlText = "Version not declared";
            XMLStreamReader r = XMLInputFactory.newInstance().createXMLStreamReader(new ByteArrayInputStream(xmlText.getBytes()));
            String version = r.getVersion();
            System.out.println("Version for text \"" + xmlText + "\": " + version);
            if (version != null) {
                Assert.fail("getVersion should return null");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occured: " + e.getMessage());
        }
    }
}
