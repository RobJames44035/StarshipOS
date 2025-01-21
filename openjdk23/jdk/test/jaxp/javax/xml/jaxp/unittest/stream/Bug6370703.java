/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6370703
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.Bug6370703
 * @summary Test StAX parser can parse attribute default value when START_ELEMENT.
 */
public class Bug6370703 {

    private static String INPUT_FILE = "sgml.xml";

    @Test
    public void testStartElement() {
        try {
            XMLInputFactory xif = XMLInputFactory.newInstance();
            XMLStreamReader xsr = xif.createXMLStreamReader(this.getClass().getResource(INPUT_FILE).toExternalForm(),
                    this.getClass().getResourceAsStream(INPUT_FILE));

            while (xsr.hasNext()) {
                int event = xsr.next();
                if (event == XMLStreamReader.START_ELEMENT) {
                    String localName = xsr.getLocalName();
                    boolean print = "para".equals(localName);
                    int nrOfAttr = xsr.getAttributeCount();
                    if (print) {
                        Assert.assertTrue(nrOfAttr > 0, "Default attribute declared in DTD is missing");
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occured: " + e.getMessage());
        }
    }

}
