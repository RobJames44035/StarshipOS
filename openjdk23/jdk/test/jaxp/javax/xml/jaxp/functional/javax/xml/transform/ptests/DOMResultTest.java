/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package javax.xml.transform.ptests;

import static javax.xml.transform.ptests.TransformerTestConst.GOLDEN_DIR;
import static javax.xml.transform.ptests.TransformerTestConst.XML_DIR;
import static jaxp.library.JAXPTestUtilities.USER_DIR;
import static jaxp.library.JAXPTestUtilities.compareWithGold;
import static org.testng.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;

import org.testng.annotations.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * DOM parse on test file to be compared with golden output file. No Exception
 * is expected.
 */
/*
 * @test
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm javax.xml.transform.ptests.DOMResultTest
 */
public class DOMResultTest {
    /**
     * Unit test for simple DOM parsing.
     * @throws Exception If any errors occur.
     */
    @Test
    public void testcase01() throws Exception {
        String resultFile = USER_DIR  + "domresult01.out";
        String goldFile = GOLDEN_DIR  + "domresult01GF.out";
        String xsltFile = XML_DIR + "cities.xsl";
        String xmlFile = XML_DIR + "cities.xml";

        XMLReader reader = XMLReaderFactory.createXMLReader();
        SAXTransformerFactory saxTFactory
                = (SAXTransformerFactory) TransformerFactory.newInstance();
        SAXSource saxSource = new SAXSource(new InputSource(xsltFile));
        TransformerHandler handler
                = saxTFactory.newTransformerHandler(saxSource);

        DOMResult result = new DOMResult();

        handler.setResult(result);
        reader.setContentHandler(handler);
        reader.parse(xmlFile);

        Node node = result.getNode();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile))) {
            writeNodes(node, writer);
        }
        assertTrue(compareWithGold(goldFile, resultFile));
    }

    /**
     * Prints all node names, attributes to file
     * @param node a node that need to be recursively access.
     * @param bWriter file writer.
     * @throws IOException if writing file failed.
     */
    private void writeNodes(Node node, BufferedWriter bWriter) throws IOException {
        String str = "Node: " + node.getNodeName();
        bWriter.write( str, 0,str.length());
        bWriter.newLine();

        NamedNodeMap nnm = node.getAttributes();
        if (nnm != null && nnm.getLength() > 0)
            for (int i=0; i<nnm.getLength(); i++) {
                str = "AttributeName:" + ((Attr) nnm.item(i)).getName() +
                      ", AttributeValue:" +((Attr) nnm.item(i)).getValue();
                bWriter.write( str, 0,str.length());
                bWriter.newLine();
            }

        NodeList kids = node.getChildNodes();
        if (kids != null)
            for (int i=0; i<kids.getLength(); i++)
                writeNodes(kids.item(i), bWriter);
    }
}
