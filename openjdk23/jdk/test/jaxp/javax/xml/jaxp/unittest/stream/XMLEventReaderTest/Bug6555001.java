/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package stream.XMLEventReaderTest;

import java.io.StringReader;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.EntityReference;
import javax.xml.stream.events.XMLEvent;
import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6555001
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.XMLEventReaderTest.Bug6555001
 * @summary Test StAX parser replaces the entity reference as setting.
 */
public class Bug6555001 {
    private static final String XML = ""
            + "<!DOCTYPE doc SYSTEM 'file:///tmp/this/does/not/exist/but/that/is/ok' ["
            + "<!ENTITY def '<para/>'>" + "]>" + "<doc>&def;&undef;</doc>";

    @Test
    public void testReplacing() throws Exception {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty("javax.xml.stream.isReplacingEntityReferences", true);

        StringReader sr = new StringReader(XML);
        XMLEventReader reader = factory.createXMLEventReader(sr);

        boolean sawUndef = false;
        boolean sawDef = false;

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            // System.out.println("Event: " + event);
            if (event.isEntityReference()) {
                EntityReference ref = (EntityReference) event;
                if ("def".equals(ref.getName())) {
                    sawDef = true;
                } else if ("undef".equals(ref.getName())) {
                    sawUndef = true;
                } else {
                    throw new IllegalArgumentException("Unexpected entity name");
                }
            }
        }

        Assert.assertEquals(false, sawDef);
        Assert.assertEquals(true, sawUndef);
        reader.close();
    }

    @Test
    public void testNotReplacing() throws Exception {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty("javax.xml.stream.isReplacingEntityReferences", false);

        StringReader sr = new StringReader(XML);
        XMLEventReader reader = factory.createXMLEventReader(sr);

        boolean sawUndef = false;
        boolean sawDef = false;

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            // System.out.println("Event: " + event);
            if (event.isEntityReference()) {
                EntityReference ref = (EntityReference) event;
                if ("def".equals(ref.getName())) {
                    sawDef = true;
                } else if ("undef".equals(ref.getName())) {
                    sawUndef = true;
                } else {
                    throw new IllegalArgumentException("Unexpected entity name");
                }
            }
        }

        Assert.assertEquals(true, sawDef);
        Assert.assertEquals(true, sawUndef);
        reader.close();
    }
}
