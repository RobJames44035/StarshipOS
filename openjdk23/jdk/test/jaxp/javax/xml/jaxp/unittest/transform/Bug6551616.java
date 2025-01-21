/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 6551616
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm transform.Bug6551616
 * @summary Test SAX2StAXEventWriter.
 */

package transform;

import java.io.InputStream;
import java.io.StringBufferInputStream;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;

import org.testng.annotations.Test;

import com.sun.org.apache.xalan.internal.xsltc.trax.SAX2StAXEventWriter;

public class Bug6551616 {
    String _cache = "";


    @Test
    public void test() throws Exception {
        final String XML = "" + "<?xml version='1.0'?>" + "<doc xmlns:foo='http://example.com/foo/' xml:lang='us-en'><p>Test</p></doc>";

        javax.xml.parsers.SAXParserFactory saxFactory = javax.xml.parsers.SAXParserFactory.newInstance();

        javax.xml.parsers.SAXParser parser = saxFactory.newSAXParser();

        XMLOutputFactory outFactory = XMLOutputFactory.newInstance();
        XMLEventWriter writer = outFactory.createXMLEventWriter(System.out);

        SAX2StAXEventWriter handler = new SAX2StAXEventWriter(writer);

        InputStream is = new StringBufferInputStream(XML);

        parser.parse(is, handler);

        // if it doesn't blow up, it succeeded.
    }
}
