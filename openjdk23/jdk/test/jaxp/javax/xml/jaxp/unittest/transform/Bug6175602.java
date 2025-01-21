/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package transform;

import java.io.File;

import javax.xml.transform.Templates;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.testng.annotations.Test;

/*
 * @test
 * @bug 6175602
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm transform.Bug6175602
 * @summary Test compilation of MsWordXMLImport.xsl.
 */
public class Bug6175602 {

    public Bug6175602() {
    }

    @Test
    public void test926007_1() throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance();
        File f = new File(getClass().getResource("MsWordXMLImport.xsl.data").getPath());
        Templates t = factory.newTemplates(new StreamSource(f));
    }

}
