/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream.XMLStreamWriterTest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.testng.annotations.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/*
 * @test
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.XMLStreamWriterTest.DomUtilTest
 * @summary Test XMLStreamWriter writes a soap message.
 */
public class DomUtilTest {

    private XMLOutputFactory staxOut;
    private static final String INPUT_FILE1 = "message_12.xml";

    public void setup() {
        this.staxOut = XMLOutputFactory.newInstance();
        staxOut.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
    }

    @Test
    public void testSOAPEnvelope1() throws Exception {
        setup();

        File f = new File(this.getClass().getResource(INPUT_FILE1).getFile());
        System.out.println("***********" + f.getName() + "***********");
        DOMSource src = makeDomSource(f);
        Node node = src.getNode();
        XMLStreamWriter writer = staxOut.createXMLStreamWriter(new PrintStream(System.out));
        DOMUtil.serializeNode((Element) node.getFirstChild(), writer);
        writer.close();
        assert (true);
        System.out.println("*****************************************");

    }

    public static DOMSource makeDomSource(File f) throws Exception {
        InputStream is = new FileInputStream(f);
        DOMSource domSource = new DOMSource(createDOMNode(is));
        return domSource;
    }

    public static void printNode(Node node) {
        DOMSource source = new DOMSource(node);
        String msgString = null;
        try {
            Transformer xFormer = TransformerFactory.newInstance().newTransformer();
            xFormer.setOutputProperty("omit-xml-declaration", "yes");
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            Result result = new StreamResult(outStream);
            xFormer.transform(source, result);
            outStream.writeTo(System.out);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Node createDOMNode(InputStream inputStream) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setValidating(false);
        try {
            DocumentBuilder builder = dbf.newDocumentBuilder();
            try {
                return builder.parse(inputStream);
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ParserConfigurationException pce) {
            IllegalArgumentException iae = new IllegalArgumentException(pce.getMessage());
            iae.initCause(pce);
            throw iae;
        }
        return null;
    }

}
