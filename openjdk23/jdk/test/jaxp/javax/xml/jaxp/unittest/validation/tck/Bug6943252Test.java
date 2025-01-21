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
 * @bug 6943252
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.tck.Bug6943252Test
 * @summary Test Schema doesn't allow to use value more than allowed by base type.
 */
public class Bug6943252Test {
    static final String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

    @Test
    public void test() {

        String dir = Bug6943252Test.class.getResource("Bug6943252In").getPath();
        File inputs = new File(dir);
        File[] files = inputs.listFiles();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        for (int i = 0; i < files.length; i++) {
            try {
                Schema schema = schemaFactory.newSchema(new StreamSource(files[i]));
                Assert.fail(files[i].getName() + "should fail");
            } catch (SAXException e) {
                // expected
                System.out.println(files[i].getName() + ":");
                System.out.println(e.getMessage());
            }
        }

    }

}
