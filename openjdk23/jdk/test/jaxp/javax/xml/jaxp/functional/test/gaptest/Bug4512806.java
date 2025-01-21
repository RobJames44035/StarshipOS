/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package test.gaptest;

import static javax.xml.transform.OutputKeys.ENCODING;
import static javax.xml.transform.OutputKeys.INDENT;
import static org.testng.Assert.assertEquals;

import java.io.StringReader;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.testng.annotations.Test;

/*
 * @test
 * @bug 4512806
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm test.gaptest.Bug4512806
 * @summary test transformer.setOutputProperties(null)
 */
public class Bug4512806 {

    @Test
    public void testProperty() throws TransformerConfigurationException {
        /* Create a transform factory instance */
        TransformerFactory tfactory = TransformerFactory.newInstance();

        /* Create a StreamSource instance */
        StreamSource streamSource = new StreamSource(new StringReader(xslData));

        transformer = tfactory.newTransformer(streamSource);
        transformer.setOutputProperty(INDENT, "no");
        transformer.setOutputProperty(ENCODING, "UTF-16");

        assertEquals(printPropertyValue(INDENT), "indent=no");
        assertEquals(printPropertyValue(ENCODING), "encoding=UTF-16");

        transformer.setOutputProperties(null);

        assertEquals(printPropertyValue(INDENT), "indent=yes");
        assertEquals(printPropertyValue(ENCODING), "encoding=UTF-8");

    }

    private String printPropertyValue(String name) {
        return name + "=" + transformer.getOutputProperty(name);
    }

    private Transformer transformer;

    private static final String xslData = "<?xml version='1.0'?>"
            + "<xsl:stylesheet"
            + " version='1.0'"
            + " xmlns:xsl='http://www.w3.org/1999/XSL/Transform'"
            + ">\n"
            + "   <xsl:output method='xml' indent='yes'"
            + " encoding='UTF-8'/>\n"
            + "   <xsl:template match='/'>\n"
            + "     Hello World! \n"
            + "   </xsl:template>\n"
            + "</xsl:stylesheet>";


}
