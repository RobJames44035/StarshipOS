/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package transform;

import org.testng.annotations.Test;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/*
 * @test
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm transform.DOMResultTest
 * @summary Test DOMResult.
 */
public class DOMResultTest {

    @Test
    public void testDOMResult1() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            String xml = this.getClass().getResource("toys.xml").getFile();
            Document doc = db.parse(new FileInputStream(new File(xml)));
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            // get <toys> element node
            Node toys = doc.getChildNodes().item(1);
            // supposed to insert new node at index=4
            int index = 4;
            String systemId = "customSysId";
            DOMResult result = new DOMResult(toys, systemId);
            result.setNextSibling(result.getNode().getChildNodes().item(index));
            int length = result.getNode().getChildNodes().getLength();
            // copy the first <toy> element node and insert it to position
            // marked by index
            Node source = doc.getLastChild().getChildNodes().item(1);
            tf.transform(new DOMSource(source), result);

            // document length verification
            if (result.getNode().getChildNodes().getLength() != length + 1) {
                Assert.fail("incorrect nodes length");
            }
            // element content verification
            Node newnode = result.getNode().getChildNodes().item(index);
            System.out.println(newnode.getTextContent());
            if (!source.getTextContent().equals(newnode.getTextContent())) {
                Assert.fail("target node content is not matched with source");
            }
            // element systemid verification
            if (!result.getSystemId().equals(systemId)) {
                Assert.fail("systemId is not matched");
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch (TransformerException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testDOMResult2() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            String xml = this.getClass().getResource("toys.xml").getFile();
            Document doc = db.parse(new FileInputStream(new File(xml)));
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            // get <toys> element node
            Node toys = doc.getChildNodes().item(1);
            // supposed to insert new node at index=4
            int index = 4;
            String systemId = "customSysId";
            DOMResult result = new DOMResult(toys, toys.getChildNodes().item(index), systemId);
            int length = result.getNode().getChildNodes().getLength();
            // copy the first <toy> element node and insert it to position
            // marked by index
            Node source = doc.getLastChild().getChildNodes().item(1);
            tf.transform(new DOMSource(source), result);

            // document length verification
            if (result.getNode().getChildNodes().getLength() != length + 1) {
                Assert.fail("incorrect nodes length");
            }
            // element content verification
            Node newnode = result.getNode().getChildNodes().item(index);
            System.out.println(newnode.getTextContent());
            if (!source.getTextContent().equals(newnode.getTextContent())) {
                Assert.fail("target node content is not matched with source");
            }
            // element systemid verification
            if (!result.getSystemId().equals(systemId)) {
                Assert.fail("systemId is not matched");
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch (TransformerException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testDOMResult3() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            String xml = this.getClass().getResource("toys.xml").getFile();
            Document doc = db.parse(new FileInputStream(new File(xml)));
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            // get <toys> element node
            Node toys = doc.getChildNodes().item(1);
            // supposed to insert new node at index=4
            int index = 4;
            DOMResult result = new DOMResult(toys, toys.getChildNodes().item(index));
            int length = result.getNode().getChildNodes().getLength();
            // copy the first <toy> element node and insert it to position
            // marked by index
            Node source = doc.getLastChild().getChildNodes().item(1);
            tf.transform(new DOMSource(source), result);

            // document length verification
            if (result.getNode().getChildNodes().getLength() != length + 1) {
                Assert.fail("incorrect nodes length");
            }
            // element content verification
            Node newnode = result.getNode().getChildNodes().item(index);
            System.out.println(newnode.getTextContent());
            if (!source.getTextContent().equals(newnode.getTextContent())) {
                Assert.fail("target node content is not matched with source");
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch (TransformerException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

}
