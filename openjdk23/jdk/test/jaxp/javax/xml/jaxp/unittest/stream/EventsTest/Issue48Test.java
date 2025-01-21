/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream.EventsTest;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.EntityDeclaration;
import javax.xml.stream.events.NotationDeclaration;
import javax.xml.stream.events.XMLEvent;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6620632
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.EventsTest.Issue48Test
 * @summary Test XMLEventReader can parse notation and entity information from DTD Event.
 */
public class Issue48Test {

    public java.io.File input;
    public final String filesDir = "./";
    protected XMLInputFactory inputFactory;
    protected XMLOutputFactory outputFactory;

    /**
     * DTDEvent instances constructed via event reader are missing the notation
     * and entity declaration information
     */
    @Test
    public void testDTDEvent() {
        String XML = "<?xml version='1.0' ?>" + "<!DOCTYPE root [\n" + "<!ENTITY intEnt 'internal'>\n" + "<!ENTITY extParsedEnt SYSTEM 'url:dummy'>\n"
                + "<!NOTATION notation PUBLIC 'notation-public-id'>\n" + "<!NOTATION notation2 SYSTEM 'url:dummy'>\n"
                + "<!ENTITY extUnparsedEnt SYSTEM 'url:dummy2' NDATA notation>\n" + "]>" + "<root />";

        try {
            XMLEventReader er = getReader(XML);
            XMLEvent evt = er.nextEvent(); // StartDocument
            evt = er.nextEvent(); // DTD
            if (evt.getEventType() != XMLStreamConstants.DTD) {
                Assert.fail("Expected DTD event");
            }
            DTD dtd = (DTD) evt;
            List entities = dtd.getEntities();
            if (entities == null) {
                Assert.fail("No entity found. Expected 3.");
            } else {
                Assert.assertEquals(entities.size(), 3);
            }
            // Let's also verify they are all of right type...
            testListElems(entities, EntityDeclaration.class);

            List notations = dtd.getNotations();
            if (notations == null) {
                Assert.fail("No notation found. Expected 2.");
            } else {
                Assert.assertEquals(notations.size(), 2);
            }
            // Let's also verify they are all of right type...
            testListElems(notations, NotationDeclaration.class);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    private XMLEventReader getReader(String XML) throws Exception {
        inputFactory = XMLInputFactory.newInstance();

        // Check if event reader returns the correct event
        XMLEventReader er = inputFactory.createXMLEventReader(new StringReader(XML));
        return er;
    }


    private void testListElems(List l, Class expType) {
        Iterator it = l.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            Assert.assertNotNull(o);
            Assert.assertTrue(expType.isAssignableFrom(o.getClass()));
        }
    }

}
