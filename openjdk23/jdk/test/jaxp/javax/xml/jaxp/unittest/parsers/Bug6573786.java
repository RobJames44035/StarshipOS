/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package parsers;

import java.io.InputStream;
import java.io.StringBufferInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6573786
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm parsers.Bug6573786
 * @summary Test parser error messages are formatted.
 */
public class Bug6573786 {
    String _cache = "";

    @Test
    public void test() {
        final String XML = "" + "<?xml version='1.0' encoding='UTF-8' standalone='bad_value' ?>" + "<root />";

        runTest(XML);

    }

    @Test
    public void test1() {
        final String XML = "" + "<?xml version='1.0' standalone='bad_value' encoding='UTF-8' ?>" + "<root />";
        runTest(XML);

    }

    void runTest(String xmlString) {
        Bug6573786ErrorHandler handler = new Bug6573786ErrorHandler();
        try {
            InputStream is = new StringBufferInputStream(xmlString);
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(is, handler);
        } catch (Exception e) {
            if (handler.fail) {
                Assert.fail("The value of standalone attribute should be merged into the error message.");
            }
        }

    }
}
