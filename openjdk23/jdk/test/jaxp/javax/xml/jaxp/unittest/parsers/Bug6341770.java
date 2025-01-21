/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package parsers;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;
import javax.xml.parsers.SAXParserFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import static jaxp.library.JAXPTestUtilities.USER_DIR;

/*
 * @test
 * @bug 6341770
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm parsers.Bug6341770
 * @summary Test external entity linked to non-ASCII base URL.
 */
public class Bug6341770 {

    // naming a file "aux" would fail on windows.
    @Test
    public void testNonAsciiURI() {
        if (!isNonAsciiSupported()) {
            // @bug 8167478
            // if it doesn't support non-ascii, the following test is invalid even if test is passed.
            System.out.println("Current environment doesn't support non-ascii, exit the test.");
            return;
        }
        try {
            File dir = new File(USER_DIR + ALPHA);
            dir.delete();
            dir.mkdir();
            File main = new File(dir, "main.xml");
            PrintWriter w = new PrintWriter(new FileWriter(main));
            w.println("<!DOCTYPE r [<!ENTITY aux SYSTEM \"aux1.xml\">]>");
            w.println("<r>&aux;</r>");
            w.flush();
            w.close();
            File aux = new File(dir, "aux1.xml");
            w = new PrintWriter(new FileWriter(aux));
            w.println("<x/>");
            w.flush();
            w.close();
            System.out.println("Parsing: " + main);
            SAXParserFactory.newInstance().newSAXParser().parse(main, new DefaultHandler() {
                public void startElement(String uri, String localname, String qname, Attributes attr)
                        throws SAXException {
                    System.out.println("encountered <" + qname + ">");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception: " + e.getMessage());
        }
        System.out.println("OK.");
    }

    private boolean isNonAsciiSupported() {
        // Use Paths.get method to test if the path is valid in current environment
        try {
            Paths.get(ALPHA);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Select alpha because it's a very common non-ascii character in different charsets.
    // That this test can run in as many as possible environments if it's possible.
    private static final String ALPHA = "\u03b1";
}
