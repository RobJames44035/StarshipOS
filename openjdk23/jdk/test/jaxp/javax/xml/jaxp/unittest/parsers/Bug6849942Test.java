/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package parsers;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.ProcessingInstruction;
import org.xml.sax.InputSource;

/*
 * @test
 * @bug 6849942
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm parsers.Bug6849942Test
 * @summary Test parsing an XML that starts with a processing instruction and no prolog.
 */
public class Bug6849942Test {

    @Test
    public void test() throws Exception {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream("<?xmltarget foo?><test></test>".getBytes());
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder xmlParser = factory.newDocumentBuilder();
            // DOMParser p = new DOMParser();
            Document document = xmlParser.parse(new InputSource(bais));
            String result = ((ProcessingInstruction) document.getFirstChild()).getData();
            System.out.println(result);
            if (!result.equalsIgnoreCase("foo")) {
                Assert.fail("missing PI data");
            }

        } catch (Exception e) {
        }
    }

    @Test
    public void testWProlog() throws Exception {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream("<?xml version=\"1.1\" encoding=\"UTF-8\"?><?xmltarget foo?><test></test>".getBytes());
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder xmlParser = factory.newDocumentBuilder();
            // DOMParser p = new DOMParser();
            Document document = xmlParser.parse(new InputSource(bais));
            String result = ((ProcessingInstruction) document.getFirstChild()).getData();
            System.out.println(result);
            if (!result.equalsIgnoreCase("foo")) {
                Assert.fail("missing PI data");
            }
        } catch (Exception e) {
        }
    }
}
