/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import javax.xml.stream.XMLOutputFactory;
import org.testng.annotations.Test;

/**
 * @test
 * @bug 8020430
 * @summary test that setProperty for XMLOutputFactory works properly
 * @run testng JAXP15RegTest
 */
public class JAXP15RegTest {

    /**
     * Verifies no Exception is thrown with the setProperty method.
     * @throws Exception if the test fails
     */
    @Test
    public void testXMLOutputFactory() throws Exception {
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        factory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
    }
}
