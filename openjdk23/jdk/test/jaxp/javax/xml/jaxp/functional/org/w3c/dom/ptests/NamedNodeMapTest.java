/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */
package org.w3c.dom.ptests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.w3c.dom.ptests.DOMTestUtil.createDOMWithNS;

import org.testng.annotations.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * @test
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm org.w3c.dom.ptests.NamedNodeMapTest
 * @summary Test for the methods of NamedNodeMap Interface
 */
public class NamedNodeMapTest {
    /*
     * Test setNamedItemNS method with a node having the same namespaceURI and
     * qualified name as an existing one, and then test with a non-existing node.
     */
    @Test
    public void testSetNamedItemNS() throws Exception {
        final String nsURI = "urn:BooksAreUs.org:BookInfo";
        Document document = createDOMWithNS("NamedNodeMap01.xml");
        NodeList nodeList = document.getElementsByTagName("body");
        nodeList = nodeList.item(0).getChildNodes();
        Node n = nodeList.item(3);

        NamedNodeMap namedNodeMap = n.getAttributes();

        // creating an Attribute using createAttributeNS
        // method having the same namespaceURI
        // and the same qualified name as the existing one in the xml file
        Attr attr = document.createAttributeNS(nsURI, "b:style");
        // setting to a new Value
        attr.setValue("newValue");
        Node replacedAttr = namedNodeMap.setNamedItemNS(attr); // return the replaced attr
        assertEquals(replacedAttr.getNodeValue(), "font-family");
        Node updatedAttr = namedNodeMap.getNamedItemNS(nsURI, "style");
        assertEquals(updatedAttr.getNodeValue(), "newValue");


        // creating a non existing attribute node
        attr = document.createAttributeNS(nsURI, "b:newNode");
        attr.setValue("newValue");

        assertNull(namedNodeMap.setNamedItemNS(attr)); // return null

        // checking if the node could be accessed
        // using the getNamedItemNS method
        Node newAttr = namedNodeMap.getNamedItemNS(nsURI, "newNode");
        assertEquals(newAttr.getNodeValue(), "newValue");
    }

    /*
     * Verify getNamedItemNS works as the spec
     */
    @Test
    public void testGetNamedItemNS() throws Exception {
        Document document = createDOMWithNS("NamedNodeMap03.xml");
        NodeList nodeList = document.getElementsByTagName("body");
        nodeList = nodeList.item(0).getChildNodes();
        Node n = nodeList.item(7);
        NamedNodeMap namedNodeMap = n.getAttributes();
        Node node = namedNodeMap.getNamedItemNS("urn:BooksAreUs.org:BookInfo", "aaa");
        assertEquals(node.getNodeValue(), "value");

    }

    /*
     * Test setNamedItem method with a node having the same name as an existing
     * one, and then test with a non-existing node.
     */
    @Test
    public void testSetNamedItem() throws Exception {
        Document document = createDOMWithNS("NamedNodeMap03.xml");
        NodeList nodeList = document.getElementsByTagName("body");
        nodeList = nodeList.item(0).getChildNodes();
        Node n = nodeList.item(1);

        NamedNodeMap namedNodeMap = n.getAttributes();
        Attr attr = document.createAttribute("name");
        Node replacedAttr = namedNodeMap.setNamedItem(attr);
        assertEquals(replacedAttr.getNodeValue(), "attributeValue");
        Node updatedAttrNode = namedNodeMap.getNamedItem("name");
        assertEquals(updatedAttrNode.getNodeValue(), "");

        Attr newAttr = document.createAttribute("nonExistingName");
        assertNull(namedNodeMap.setNamedItem(newAttr));
        Node newAttrNode = namedNodeMap.getNamedItem("nonExistingName");
        assertEquals(newAttrNode.getNodeValue(), "");
    }

}
