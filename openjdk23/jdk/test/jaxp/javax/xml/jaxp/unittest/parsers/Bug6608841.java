/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package parsers;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * @test
 * @bug 6608841
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm parsers.Bug6608841
 * @summary Test SAX parses external parameter entity.
 */
public class Bug6608841 {
    public Bug6608841(String name) {
    }

    @Test
    public void testParse() throws ParserConfigurationException, SAXException, IOException {
        String file = getClass().getResource("Bug6608841.xml").getFile();
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser parser = spf.newSAXParser();
        parser.parse(new File(file), new MyHandler());
    }

    public class MyHandler extends DefaultHandler {
    }
}
