/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package parsers;

import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

/*
 * @test
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm parsers.ParseEmptyStream
 * @summary Test SAXParser doesn't accept empty stream.
 */
public class ParseEmptyStream {

    SAXParserFactory factory = null;

    public ParseEmptyStream(String name) {
        try {
            factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testEmptyStream() {
        try {
            SAXParser parser = factory.newSAXParser();
            InputSource source = new InputSource(new StringReader(""));
            parser.parse(source, new MyHandler());
            Assert.fail("Inputstream without document element accepted");
        } catch (Exception ex) {
            System.out.println("Exception thrown: " + ex.getMessage());
            // Premature end of file exception expected
        }
    }

    @Test
    public void testXmlDeclOnly() {
        try {
            SAXParser parser = factory.newSAXParser();
            InputSource source = new InputSource(new StringReader("<?xml version='1.0' encoding='utf-8'?>"));
            parser.parse(source, new MyHandler());
            Assert.fail("Inputstream without document element accepted");
        } catch (Exception ex) {
            System.out.println("Exception thrown: " + ex.getMessage());
            // Premature end of file exception expected
        }
    }

    static class MyHandler extends DefaultHandler {
        public void startDocument() {
            System.out.println("Start document called");
        }

        public void endDocument() {
            System.out.println("End document called");
        }
    }

}
