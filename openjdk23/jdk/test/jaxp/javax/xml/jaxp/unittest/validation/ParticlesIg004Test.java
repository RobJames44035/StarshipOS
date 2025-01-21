/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.ParticlesIg004Test
 * @summary Test particlesIg004.xsd.
 */
public class ParticlesIg004Test {

    @Test
    public void testParticleslg004() {
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            String xsdFile = "particlesIg004.xsd";
            Schema schema = sf.newSchema(new File(getClass().getResource(xsdFile).toURI()));
            Validator validator = schema.newValidator();
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }
}
