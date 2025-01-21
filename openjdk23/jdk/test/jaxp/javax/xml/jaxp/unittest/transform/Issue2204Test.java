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
 * @bug 6905829
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm transform.Issue2204Test
 * @summary Test XSLT can work against the certain xsl.
 */
public class Issue2204Test {

    @Test
    public final void testTransform() {
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer(new StreamSource(getClass().getResourceAsStream("Issue2204.xsl")));

            System.out.printf("transformer: %s%n", t.getClass().getName());

            StringWriter streamResult = new StringWriter();
            t.transform(new StreamSource(getClass().getResourceAsStream("Issue2204.xml")), new StreamResult(streamResult));

            System.out.println(streamResult.toString());
            if (streamResult.toString().indexOf("3") > 0) {
                Assert.fail("Function Count on variable modifies number of nodes in variable.");
            }
            // expected success
        } catch (Exception e) {
            // unexpected failure
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }
}
