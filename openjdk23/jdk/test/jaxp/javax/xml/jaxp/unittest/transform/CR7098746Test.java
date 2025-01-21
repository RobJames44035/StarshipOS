/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package transform;

import java.io.StringWriter;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 7098746
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm transform.CR7098746Test
 * @summary Test transforming as expected.
 */
public class CR7098746Test {

    @Test
    public final void testTransform() {

        try {

            String inFilename = "CR7098746.xml";
            String xslFilename = "CR7098746.xsl";

            StringWriter sw = new StringWriter();
            // Create transformer factory
            TransformerFactory factory = TransformerFactory.newInstance();
            // set the translet name
            // factory.setAttribute("translet-name", "myTranslet");

            // set the destination directory
            // factory.setAttribute("destination-directory", "c:\\temp");
            // factory.setAttribute("generate-translet", Boolean.TRUE);

            // Use the factory to create a template containing the xsl file
            Templates template = factory.newTemplates(new StreamSource(getClass().getResourceAsStream(xslFilename)));
            // Use the template to create a transformer
            Transformer xformer = template.newTransformer();
            // Prepare the input and output files
            Source source = new StreamSource(getClass().getResourceAsStream(inFilename));
            // Result result = new StreamResult(new
            // FileOutputStream(outFilename));
            Result result = new StreamResult(sw);
            // Apply the xsl file to the source file and write the result to the
            // output file
            xformer.transform(source, result);

            String out = sw.toString();
            if (out.indexOf("<p>") < 0) {
                Assert.fail(out);
            }
        } catch (Exception e) {
            // unexpected failure
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }
}
