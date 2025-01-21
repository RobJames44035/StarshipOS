/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation.tck;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

/*
 * @test
 * @bug 6967214
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.tck.Bug6967214Test
 * @summary Test Schema doesn't allow unpaired parenthesises in regex.
 */
public class Bug6967214Test {
    static final String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

    @Test
    public void test() {
        try {
            File dir = new File(Bug6967214Test.class.getResource("Bug6967214").getPath());
            File files[] = dir.listFiles();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            for (int i = 0; i < files.length; i++) {
                try {
                    System.out.println(files[i].getName());
                    Schema schema = schemaFactory.newSchema(new StreamSource(files[i]));
                    Assert.fail("should report error");
                } catch (org.xml.sax.SAXParseException spe) {
                    continue;
                }
            }
        } catch (SAXException e) {
            e.printStackTrace();

        }
    }

}
