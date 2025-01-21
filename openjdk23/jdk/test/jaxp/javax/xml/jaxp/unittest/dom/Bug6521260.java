/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package dom;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/*
 * @test
 * @bug 6521260
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm dom.Bug6521260
 * @summary Test setAttributeNS doesn't result in an unsorted internal list of attributes.
 */
public class Bug6521260 {

    @Test
    public void test() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        String docStr = "<system systemId='http://www.w3.org/2001/rddl/rddl-xhtml.dtd'" + " uri='/cache/data/xrc36316.bin'"
                + " xmlns:xr='urn:oasis:names:tc:entity:xmlns:xml:catalog'" + " xr:systemId='http://www.w3.org/2001/rddl/rddl-xhtml.dtd'"
                + " xmlns:NS1='http://xmlresolver.org/ns/catalog'" + " NS1:time='1170267571097'/>";

        ByteArrayInputStream bais = new ByteArrayInputStream(docStr.getBytes());

        Document doc = builder.parse(bais);

        Element root = doc.getDocumentElement();

        String systemId = root.getAttribute("systemId");

        // Change the prefix on the "time" attribute so that the list would
        // become unsorted
        // before my fix to
        // xml-xerces/java/src/com/sun/org/apache/xerces/internal/dom/ElementImpl.java
        root.setAttributeNS("http://xmlresolver.org/ns/catalog", "xc:time", "100");

        String systemId2 = root.getAttribute("systemId");

        Assert.assertEquals(systemId, systemId2);
    }
}
