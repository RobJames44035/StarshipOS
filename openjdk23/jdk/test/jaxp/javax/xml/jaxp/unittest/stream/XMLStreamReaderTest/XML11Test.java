/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream.XMLStreamReaderTest;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.XMLStreamReaderTest.XML11Test
 * @summary Test parsing xml 1.1.
 */
public class XML11Test {

    @Test
    public void test() {
        try {
            XMLInputFactory xif = XMLInputFactory.newInstance();
            XMLEventReader reader = xif.createXMLEventReader(this.getClass().getResourceAsStream("xml11.xml.data"));
            while (reader.hasNext())
                reader.next();

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }
}
