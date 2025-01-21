/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package transform;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMResult;

import org.testng.annotations.Test;
import org.w3c.dom.Document;

/*
 * @test
 * @bug 5073477
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm transform.Bug5073477
 * @summary Test DOMResult.setNextSibling works correctly.
 */
public class Bug5073477 {

    @Test
    public void test1() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder parser = dbf.newDocumentBuilder();
        Document dom = parser.parse(Bug5073477.class.getResourceAsStream("Bug5073477.xml"));

        DOMResult r = new DOMResult();

        r.setNode(dom.getDocumentElement());
        r.setNextSibling(r.getNode().getFirstChild());
    }
}
