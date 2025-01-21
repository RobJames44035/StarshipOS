/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package parsers;

import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/*
 * @test
 * @bug 6518733
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm parsers.Bug6690015
 * @summary Test SAX parser handles several attributes with newlines.
 */
public class Bug6690015 {

    public Bug6690015() {
    }

    @Test
    public void test() {
        try {
            FileInputStream fis = new FileInputStream(getClass().getResource("bug6690015.xml").getFile());

            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(fis));
            Element root = doc.getDocumentElement();
            NodeList textnodes = root.getElementsByTagName("text");
            int len = textnodes.getLength();
            int index = 0;
            int attindex = 0;
            int attrlen = 0;
            NamedNodeMap attrs = null;

            while (index < len) {
                Element te = (Element) textnodes.item(index);
                attrs = te.getAttributes();
                attrlen = attrs.getLength();
                attindex = 0;
                Node node = null;

                while (attindex < attrlen) {
                    node = attrs.item(attindex);
                    System.out.println("attr: " + node.getNodeName() + " is shown holding value: " + node.getNodeValue());
                    attindex++;
                }
                index++;
                System.out.println("-------------");
            }
            fis.close();
        } catch (Exception e) {
            Assert.fail("Exception: " + e.getMessage());
        }
    }

}
