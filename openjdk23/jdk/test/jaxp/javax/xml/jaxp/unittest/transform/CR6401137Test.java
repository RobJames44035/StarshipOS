/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package transform;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6401137
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm transform.CR6401137Test
 * @summary Test transform certain xsl.
 */
public class CR6401137Test {

    @Test
    public final void testTransform() {

        try {
            String str = new String();
            ByteArrayOutputStream byte_stream = new ByteArrayOutputStream();
            File inputFile = new File(getClass().getResource("CR6401137.xml").getPath());
            FileReader in = new FileReader(inputFile);
            int c;

            while ((c = in.read()) != -1) {
                str = str + new Character((char) c).toString();
            }

            in.close();

            System.out.println(str);
            byte buf[] = str.getBytes();
            byte_stream.write(buf);
            String style_sheet_uri = "CR6401137.xsl";
            byte[] xml_byte_array = byte_stream.toByteArray();
            InputStream xml_input_stream = new ByteArrayInputStream(xml_byte_array);

            Source xml_source = new StreamSource(xml_input_stream);

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            StreamSource source = new StreamSource(getClass().getResourceAsStream(style_sheet_uri));
            transformer = tFactory.newTransformer(source);

            ByteArrayOutputStream result_output_stream = new ByteArrayOutputStream();
            Result result = new StreamResult(result_output_stream);
            transformer.transform(xml_source, result);
            result_output_stream.close();

            // expected success
        } catch (Exception e) {
            // unexpected failure
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }
}
