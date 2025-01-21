/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package transform;

import java.io.CharArrayWriter;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6689809
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm transform.CR6689809Test
 * @summary Test Transformer can handle XPath predicates in xsl:key elements.
 */
public class CR6689809Test {

    @Test
    public final void testTransform() {

        try {
            StreamSource input = new StreamSource(getClass().getResourceAsStream("PredicateInKeyTest.xml"));
            StreamSource stylesheet = new StreamSource(getClass().getResourceAsStream("PredicateInKeyTest.xsl"));
            CharArrayWriter buffer = new CharArrayWriter();
            StreamResult output = new StreamResult(buffer);

            TransformerFactory.newInstance().newTransformer(stylesheet).transform(input, output);

            Assert.assertEquals(buffer.toString(), "0|1|2|3", "XSLT xsl:key implementation is broken!");
            // expected success
        } catch (Exception e) {
            // unexpected failure
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }
}
