/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package dom.ls;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSException;

/*
 * @test
 * @bug 6710741
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm dom.ls.Bug6710741Test
 * @summary Test there should be stack trace information if LSSerializer().writeToString reports an exception.
 */
public class Bug6710741Test {

    @Test
    public final void test() {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element el = doc.createElement("x");
            DOMImplementationLS ls = (DOMImplementationLS) doc.getImplementation().getFeature("LS", "3.0");
            System.out.println(ls.createLSSerializer().writeToString(el));
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        } catch (LSException ex) {
            ex.printStackTrace();
            System.out.println("cause: " + ex.getCause());
            if (ex.getCause() == null) {
                Assert.fail("should set cause.");
            }
        }
    }

    @Test
    public void testWorkaround() {
        Document doc;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element el = doc.createElement("x");
            doc.appendChild(el);
            DOMImplementationLS ls = (DOMImplementationLS) doc.getImplementation().getFeature("LS", "3.0");
            System.out.println(ls.createLSSerializer().writeToString(doc));
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

}
