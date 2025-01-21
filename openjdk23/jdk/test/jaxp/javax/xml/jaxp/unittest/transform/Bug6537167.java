/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package transform;

import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.testng.annotations.Test;

/*
 * @test
 * @bug 6537167
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm transform.Bug6537167
 * @summary Test transforming for particular xsl files.
 */
public class Bug6537167 {

    @Test
    public void test926007_1() throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance();
        File f = new File(getClass().getResource("logon.xsl").getPath());
        Templates t = factory.newTemplates(new StreamSource(f));
        Transformer transformer = t.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        transformer.transform(new StreamSource(getClass().getResourceAsStream("src.xml")), new StreamResult(System.out));
    }

    @Test
    public void test926007_2() throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance();
        // factory.setAttribute("generate-translet", Boolean.TRUE);
        File f = new File(getClass().getResource("home.xsl").getPath());
        Templates t = factory.newTemplates(new StreamSource(f));
        Transformer transformer = t.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        transformer.transform(new StreamSource(getClass().getResourceAsStream("src.xml")), new StreamResult(System.out));
    }

    @Test
    public void test926007_3() throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance();
        // factory.setAttribute("generate-translet", Boolean.TRUE);
        File f = new File(getClass().getResource("upload-media.xsl").getPath());
        Templates t = factory.newTemplates(new StreamSource(f));
        Transformer transformer = t.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        transformer.transform(new StreamSource(getClass().getResourceAsStream("src.xml")), new StreamResult(System.out));
    }

}
