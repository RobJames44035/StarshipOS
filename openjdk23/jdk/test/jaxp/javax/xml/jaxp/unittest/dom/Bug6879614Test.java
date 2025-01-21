/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package dom;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/*
 * @test
 * @bug 6879614
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm dom.Bug6879614Test
 * @summary Test DocumentBuilder can parse the certain xml.
 */
public class Bug6879614Test {

    @Test
    public void testAttributeCaching() {
        File xmlFile = new File(getClass().getResource("Bug6879614.xml").getFile());
        DocumentBuilderFactory _documentBuilderFactory = DocumentBuilderFactory.newInstance();
        _documentBuilderFactory.setValidating(false);
        _documentBuilderFactory.setIgnoringComments(true);
        _documentBuilderFactory.setIgnoringElementContentWhitespace(true);
        _documentBuilderFactory.setCoalescing(true);
        _documentBuilderFactory.setExpandEntityReferences(true);
        _documentBuilderFactory.setNamespaceAware(true);
        DocumentBuilder _documentBuilder = null;
        try {
            _documentBuilder = _documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }

        Document xmlDoc = null;
        try {
            xmlDoc = _documentBuilder.parse(xmlFile);
            if (xmlDoc == null) {
                System.out.println("Hello!!!, there is a problem here");
            } else {
                System.out.println("Good, the parsing went through fine.");
            }
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
