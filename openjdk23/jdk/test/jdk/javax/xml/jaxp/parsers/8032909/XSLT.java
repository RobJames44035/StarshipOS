/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8032909
 * @summary Test for XSLT string-length function with complementary chars
 * @compile XSLT.java
 * @run main/othervm XSLT a_utf16.xml a_utf16.xsl 1270
 * @run main/othervm XSLT a_utf8.xml a_utf8.xsl 130
 * @run main/othervm XSLT a_windows1252.xml a_windows1252.xsl 200
 */

import java.io.ByteArrayOutputStream;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

public class XSLT {
    public static void main(String[] args) throws Exception {

        ByteArrayOutputStream resStream = new ByteArrayOutputStream();
        TransformerFactory trf = TransformerFactory.newInstance();
        Transformer tr = trf.newTransformer(new StreamSource(System.getProperty("test.src", ".")+"/"+args[1]));
        String res, expectedRes;
        tr.transform( new StreamSource(System.getProperty("test.src", ".")+"/"+args[0]), new StreamResult(resStream));
        res = resStream.toString();
        System.out.println("Transformation completed. Result:"+res);

        if (!res.replaceAll("\\s","").equals(args[2]))
            throw new RuntimeException("Incorrect transformation result. Expected:"+args[2]+" Observed:"+res);
    }
}
