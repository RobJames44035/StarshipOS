/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream;

import java.io.StringReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.testng.annotations.Test;

/*
 * @test
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.IgnoreExternalDTDTest
 * @summary Test feature ignore-external-dtd.
 */
public class IgnoreExternalDTDTest {

    final static String FACTORY_KEY = "javax.xml.stream.XMLInputFactory";
    static final String IGNORE_EXTERNAL_DTD = "ignore-external-dtd";
    static final String ZEPHYR_PROPERTY_PREFIX = "http://java.sun.com/xml/stream/properties/";

    @Test
    public void testFeaturePositive() throws Exception {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        xif.setProperty(ZEPHYR_PROPERTY_PREFIX + IGNORE_EXTERNAL_DTD, Boolean.TRUE);
        parse(xif);
    }

    @Test
    public void testFeatureNegative() throws Exception {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        xif.setProperty(ZEPHYR_PROPERTY_PREFIX + IGNORE_EXTERNAL_DTD, Boolean.FALSE);
        try {
            parse(xif);
            // refer to 6440324, absent of that change, an exception would be
            // thrown;
            // due to the change made for 6440324, parsing will continue without
            // exception
            // fail();
        } catch (XMLStreamException e) {
            // the error is expected that no DTD was found
        }
    }

    private void parse(XMLInputFactory xif) throws XMLStreamException {
        XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader("<?xml version='1.0'?><!DOCTYPE root PUBLIC 'abc' 'def'><abc />"));
        while (xsr.next() != XMLStreamConstants.END_DOCUMENT)
            ;
    }

}
