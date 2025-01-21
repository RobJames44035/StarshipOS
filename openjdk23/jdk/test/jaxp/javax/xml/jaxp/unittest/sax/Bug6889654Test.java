/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package sax;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * @test
 * @bug 6889654
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm sax.Bug6889654Test
 * @summary Test SAXException includes whole information.
 */
public class Bug6889654Test {

    final String MSG = "Failed to parse XML";

    @Test
    public void testException() {
        try {
            parse();
        } catch (SAXException e) {
            // e.printStackTrace();
            String msg = e.toString();
            if (msg.indexOf("systemId") == -1) {
                Assert.fail("CR6889654 -- details should be returned.");
            }
            if (msg.indexOf(MSG) == -1) {
                Assert.fail("CR6889649 -- additional error message not returned.");
            }
            System.out.println("error message:\n" + msg);
        }
    }

    void parse() throws SAXException {
        String xml = "<data>\n<broken/>\u0000</data>";

        try {
            InputSource is = new InputSource(new StringReader(xml));
            is.setSystemId("file:///path/to/some.xml");
            // notice that exception thrown here doesn't include the line number
            // information when reported by JVM -- CR6889654
            SAXParserFactory.newInstance().newSAXParser().parse(is, new DefaultHandler());
        } catch (SAXException e) {
            // notice that this message isn't getting displayed -- CR6889649
            throw new SAXException(MSG, e);
        } catch (ParserConfigurationException pce) {

        } catch (IOException ioe) {

        }

    }

}
