/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package stream.XMLStreamFilterTest;

import static org.testng.Assert.assertThrows;

import java.io.Reader;
import java.io.StringReader;

import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.testng.annotations.Test;

/*
* @test
* @bug 8255918
* @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
* @run testng stream.XMLStreamFilterTest.XMLStreamReaderFilterTest
* @summary Test the implementation of {@code XMLStreamReader} using a {@code StreamFilter}
*/
public class XMLStreamReaderFilterTest {

    static final String XMLSOURCE1 = "<root>\n"
            + "  <element1>\n"
            + "    <element2>\n" // Unclosed element2
            + "  </element1>\n"
            + "  <element3>\n"
            + "  </element3>\n"
            + "</root>";

    /**
     * Verifies that XMLStreamException is thrown as specified by the
     * {@code XMLInputFactory::createFilteredReader} method when an error
     * is encountered. This test illustrates the scenario by creating a
     * reader with a filter that requires the original reader to advance
     * past the invalid element in the underlying XML.
     *
     * @throws Exception When an unexpected exception is encountered (test failure)
     */
    @Test
    public void testCreateFilteredReader() throws Exception {
        StreamFilter filter = r -> r.getEventType() == XMLStreamConstants.START_ELEMENT
                                && r.getLocalName().equals("element3");

        XMLInputFactory factory = XMLInputFactory.newInstance();

        try (Reader source = new StringReader(XMLSOURCE1)) {
            XMLStreamReader reader = factory.createXMLStreamReader(source);
            assertThrows(XMLStreamException.class, () -> factory.createFilteredReader(reader, filter));
        }
    }

}
