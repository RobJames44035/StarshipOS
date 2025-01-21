/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package dom;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/*
 * @test
 * @bug 4915524
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm dom.Bug4915524
 * @summary Test Document.adoptNode() shall not throw Exception when the source document object is created from different implementation.
 */

public class Bug4915524 {

    String data = "<?xml version=\"1.0\" ?>" + "<!DOCTYPE root [" + "<!ELEMENT root ANY>" + "<!ATTLIST root attr1 ID #FIXED 'xxx'"
            + "               attr2 CDATA #IMPLIED> " + "]>" + "<root attr2='yyy'/>";

    DocumentBuilder docBuilder = null;

    /*
     * This method tries to adopt a node from Defered document to non-defered
     * document.
     */
    @Test
    public void testAdoptNode() {
        try {
            DocumentBuilderFactory docBF = DocumentBuilderFactory.newInstance();
            docBuilder = docBF.newDocumentBuilder();

            Document doc1 = parse(data);
            Document doc2 = docBuilder.newDocument();

            Node element = doc2.adoptNode(doc1.getDocumentElement());

            System.out.println("OK.");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Excpetion while adopting node: " + e.getMessage());
        }

    }

    private Document parse(String xmlData) throws Exception {
        StringReader in = new StringReader(xmlData);
        InputSource source = new InputSource(in);
        return docBuilder.parse(source);
    }
}
