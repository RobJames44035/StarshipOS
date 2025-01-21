/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package test.gaptest;

import java.io.StringReader;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.testng.annotations.Test;

/*
 * @test
 * @bug 4511326
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm test.gaptest.Bug4511326
 * @summary In forwards-compatible mode the attribute isn't ignored
 */
public class Bug4511326 {

    private static final String XSL = "<xsl:stylesheet version='2.0' "
                               + "xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>"
                               + "<xsl:template a='1' match='/'>"
                               + "<H2><xsl:value-of select='//author'/></H2>"
                               + "<H1><xsl:value-of select='//title'/></H1>"
                               + "</xsl:template>"
                               + "</xsl:stylesheet>";


    @Test
    public void ignoreAttTest() throws TransformerConfigurationException {
        /* Create a TransformFactory instance */
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        /* Create and init a StreamSource instance */
        StreamSource source = new StreamSource(new StringReader(XSL));

        transformerFactory.newTransformer(source);
    }

}
