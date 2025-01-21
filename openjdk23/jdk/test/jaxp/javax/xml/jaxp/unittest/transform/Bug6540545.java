/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package transform;

import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6540545
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm transform.Bug6540545
 * @summary Test XSLT as expected.
 */
public class Bug6540545 {

    @Test
    public void test() {
        try {
            String xmlFile = "numbering63.xml";
            String xslFile = "numbering63.xsl";

            TransformerFactory tFactory = TransformerFactory.newInstance();
            // tFactory.setAttribute("generate-translet", Boolean.TRUE);
            Transformer t = tFactory.newTransformer(new StreamSource(getClass().getResourceAsStream(xslFile), getClass().getResource(xslFile).toString()));
            StringWriter sw = new StringWriter();
            t.transform(new StreamSource(getClass().getResourceAsStream(xmlFile)), new StreamResult(sw));
            String s = sw.getBuffer().toString();
            Assert.assertFalse(s.contains("1: Level A"));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

}
