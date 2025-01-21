/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package transform;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

/*
 * @test
 * @bug 6513892 8343001
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm transform.Bug6513892
 * @summary Test the output encoding of the transform is the same as that of the redirect extension.
 */
public class Bug6513892 {
    @Test
    public void test0() {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            tf.setFeature("jdk.xml.enableExtensionFunctions", true);
            Transformer t = tf.newTransformer(new StreamSource(getClass().getResourceAsStream("redirect.xsl"), getClass().getResource("redirect.xsl")
                    .toString()));

            StreamSource src1 = new StreamSource(getClass().getResourceAsStream("redirect.xml"));
            t.transform(src1, new StreamResult("redirect1.xml"));

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document d1 = db.parse(new File("redirect1.xml"));
            Document d2 = db.parse(new File("redirect2.xml"));

            Assert.assertTrue(d1.getDocumentElement().getFirstChild().getNodeValue().equals(d2.getDocumentElement().getFirstChild().getNodeValue()));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

}
