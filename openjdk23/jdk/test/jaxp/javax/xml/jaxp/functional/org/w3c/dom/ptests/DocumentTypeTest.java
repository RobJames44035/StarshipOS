/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package org.w3c.dom.ptests;

import static org.testng.Assert.assertEquals;
import static org.w3c.dom.ptests.DOMTestUtil.createDOM;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;

/*
 * @test
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm org.w3c.dom.ptests.DocumentTypeTest
 * @summary Test DocumentType
 */
public class DocumentTypeTest {

    /*
     * Test testGetEntities method, and verify the entity items.
     */
    @Test
    public void testGetEntities() throws Exception {
        DocumentType documentType = createDOM("DocumentType01.xml").getDoctype();
        NamedNodeMap namedNodeMap = documentType.getEntities();
        // should return both external and internal. Parameter entities are not
        // contained. Duplicates are discarded.
        assertEquals(namedNodeMap.getLength(), 3);
        assertEquals(namedNodeMap.item(0).getNodeName(), "author");
        assertEquals(namedNodeMap.item(1).getNodeName(), "test");
        assertEquals(namedNodeMap.item(2).getNodeName(), "writer");
    }

    /*
     * Test getNotations method, and verify the notation items.
     */
    @Test
    public void testGetNotations() throws Exception {
        DocumentType documentType = createDOM("DocumentType03.xml").getDoctype();
        NamedNodeMap nm = documentType.getNotations();
        assertEquals(nm.getLength(), 2); // should return 2 because the notation
                                         // name is repeated and
                                         // it considers only the first
                                         // occurence
        assertEquals(nm.item(0).getNodeName(), "gs");
        assertEquals(nm.item(1).getNodeName(), "name");
    }

    /*
     * Test getName method.
     */
    @Test
    public void testGetName() throws Exception {
        DocumentType documentType = createDOM("DocumentType03.xml").getDoctype();
        assertEquals(documentType.getName(), "note");
    }

    /*
     * Test getSystemId and getPublicId method.
     */
    @Test
    public void testGetSystemId() throws Exception {
        DocumentType documentType = createDOM("DocumentType05.xml").getDoctype();
        assertEquals(documentType.getSystemId(), "DocumentBuilderImpl02.dtd");
        Assert.assertNull(documentType.getPublicId());
    }

}
