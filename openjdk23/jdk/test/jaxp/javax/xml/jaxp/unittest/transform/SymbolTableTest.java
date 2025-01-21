/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package transform;

import java.io.StringReader;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 8264766
 * @run testng transform.SymbolTableTest
 * @summary Tests SymbolTable
 */
public class SymbolTableTest {
    /**
     * Verifies that the SymbolTable processes (adds) variables and params
     * properly. The SymbolTable holds variables and params in a map, it shall
     * therefore perform a type check before cast, or else result in a
     * ClassCastException when variables and/or params have the same name (in
     * which case the later ones shadow the previous ones).
     *
     * @throws Exception if the test fails
     */
    @Test
    public void test() throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        String stylesheet = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\">\n" +
                " <xsl:variable name=\"background-color\">#f4f4f4</xsl:variable>\n" +
                " <xsl:param name=\"background-color\">pp</xsl:param>\n" +
                " <xsl:template name=\"tName\"><xsl:param name=\"background-color\">black</xsl:param>\n" +
                " OK <xsl:value-of select=\"$background-color\"/>\n" +
                " </xsl:template>\n" +
                " <xsl:template match=\"/root\">\n" +
                " <xsl:call-template name=\"tName\">\n" +
                " <xsl:with-param name=\"background-color\" select=\"$background-color\"/>\n" +
                " </xsl:call-template>\n" +
                " </xsl:template>\n" +
                "</xsl:stylesheet>\n";
        transformerFactory.newTransformer(new StreamSource(new StringReader(stylesheet)));
    }
}
