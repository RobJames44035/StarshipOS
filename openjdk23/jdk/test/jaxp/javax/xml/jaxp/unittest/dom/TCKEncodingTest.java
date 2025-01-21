/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package dom;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/*
 * @test
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm dom.TCKEncodingTest
 * @summary Test Document.getInputEncoding().
 */
public class TCKEncodingTest {

    /**
     * Assertion testing
     * for public String getInputEncoding(),
     * An attribute specifying the actual encoding of this document..
     */
    @Test
    public void testGetInputEncoding001() {
        String data = "<?xml version=\"1.0\"?>" + "<!DOCTYPE root [" + "<!ELEMENT root ANY>" + "]>" + "<root/>";

        Document doc = null;
        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource inSource = new InputSource(new StringReader(data));
            inSource.setEncoding("UTF-8");
            inSource.setSystemId("test.xml");
            doc = docBuilder.parse(inSource);
        } catch (ParserConfigurationException e) {
            Assert.fail(e.toString());
        } catch (IOException e) {
            Assert.fail(e.toString());
        } catch (SAXException e) {
            Assert.fail(e.toString());
        }

        String encoding = doc.getInputEncoding();
        if (encoding == null || !encoding.equals("UTF-8")) {
            Assert.fail("expected encoding: UTF-8, returned: " + encoding);
        }

        System.out.println("OK");
    }

    /**
     * Assertion testing
     * for public String getInputEncoding(),
     * Encoding is not specified. getInputEncoding returns null..
     */
    @Test
    public void testGetInputEncoding002() {
        Document doc = null;
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = db.newDocument();
        } catch (ParserConfigurationException e) {
            Assert.fail(e.toString());
        }

        String encoding = doc.getInputEncoding();
        if (encoding != null) {
            Assert.fail("expected encoding: null, returned: " + encoding);
        }

        System.out.println("OK");
    }
}
