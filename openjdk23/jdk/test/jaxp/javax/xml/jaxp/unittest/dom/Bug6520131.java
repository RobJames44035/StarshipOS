/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package dom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMError;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/*
 * @test
 * @bug 6520131
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm dom.Bug6520131
 * @summary Test DOMErrorHandler reports an error for invalid character.
 */
public class Bug6520131 {

    @Test
    public void test() {
        String string = new String("\u0001");

        try {
            // create document
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            DOMConfiguration domConfig = document.getDomConfig();
            domConfig.setParameter("well-formed", Boolean.TRUE);
            domConfig.setParameter("error-handler", new DOMErrorHandler() {
                public boolean handleError(DOMError e) {
                    throw new RuntimeException(e.getMessage());
                }
            });

            // add text element
            Element textElement = document.createElementNS("", "Text");
            Text text = document.createTextNode(string);
            textElement.appendChild(text);
            document.appendChild(textElement);

            // normalize document
            document.normalizeDocument();

            Assert.fail("Invalid character exception not thrown");
        } catch (ParserConfigurationException e) {
            Assert.fail("Unable to configure parser");
        } catch (RuntimeException e) {
            // This exception is expected!
        }
    }
}
