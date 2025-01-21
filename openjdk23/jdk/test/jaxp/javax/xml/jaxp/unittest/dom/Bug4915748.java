/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package dom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.CDATASection;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMError;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.Document;

/*
 * @test
 * @bug 4915748
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm dom.Bug4915748
 * @summary Test DOMErrorHandler is called in case CDATA section is split by termination marker ']]>'.
 */
public class Bug4915748 {

    @Test
    public void testMain() throws Exception {

        final boolean[] hadError = new boolean[1];

        DocumentBuilderFactory docBF = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBF.newDocumentBuilder();

        Document doc = docBuilder.getDOMImplementation().createDocument("namespaceURI", "ns:root", null);

        CDATASection cdata = doc.createCDATASection("text1]]>text2");
        doc.getDocumentElement().appendChild(cdata);

        DOMConfiguration config = doc.getDomConfig();
        DOMErrorHandler erroHandler = new DOMErrorHandler() {
            public boolean handleError(DOMError error) {
                System.out.println(error.getMessage());
                Assert.assertEquals(error.getType(), "cdata-sections-splitted");
                Assert.assertFalse(hadError[0], "two errors were reported");
                hadError[0] = true;
                return false;
            }
        };
        config.setParameter("error-handler", erroHandler);
        doc.normalizeDocument();
        Assert.assertTrue(hadError[0]);
    }
}
