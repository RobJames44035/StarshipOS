/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package stream.XMLEventReaderTest;

import java.io.StringReader;
import java.util.NoSuchElementException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartDocument;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

/*
 * @test
 * @bug 8204329 8256515
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng stream.XMLEventReaderTest.EventReaderTest
 * @summary Tests XMLEventReader
 */
public class EventReaderTest {
    @Test(expectedExceptions = NoSuchElementException.class)
    public void testNextEvent() throws Exception {
        XMLEventReader eventReader = XMLInputFactory.newFactory().createXMLEventReader(
                new StringReader("<?xml version='1.0'?><foo/>"));

        while (eventReader.hasNext()) {
            eventReader.nextEvent();
        }
        // no more event
        eventReader.nextEvent();
    }

    @DataProvider
    Object[][] standaloneSetTestData() {
        return new Object[][]{
                {"<?xml version=\"1.0\"?>", false, false},
                {"<?xml version=\"1.0\" standalone=\"no\"?>", false, true},
                {"<?xml version=\"1.0\" standalone=\"yes\"?>", true, true}
        };
    }

    @Test(dataProvider = "standaloneSetTestData")
    void testStandaloneSet(String xml, boolean standalone, boolean standaloneSet) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader reader = factory.createXMLEventReader(new StringReader(xml));
        StartDocument startDocumentEvent = (StartDocument) reader.nextEvent();

        assertEquals(startDocumentEvent.isStandalone(), standalone);
        assertEquals(startDocumentEvent.standaloneSet(), standaloneSet);
    }
}
