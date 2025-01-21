/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation;

import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXParseException;

/*
 * @test
 * @bug 6483188
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.Bug6483188
 * @summary Test Schema Validator can handle element with having large maxOccurs, but doesn't accept sequence with having large maxOccurs in FEATURE_SECURE_PROCESSING mode.
 */
@Test(singleThreaded = true)
public class Bug6483188 {
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

    public void testLargeElementNoSecurity() {
        if (System.getSecurityManager() != null)
            return; // jaxp secure feature can not be turned off when security
                    // manager is present
        try {
            sf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, Boolean.FALSE);
            URL url = getClass().getResource("test-element.xsd");
            Schema s = sf.newSchema(url);
            Validator v = s.newValidator();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    public void testLargeElementWithSecurity() {
        try {
            sf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, Boolean.TRUE);
            URL url = getClass().getResource("test-element.xsd");
            Schema s = sf.newSchema(url);
            Validator v = s.newValidator();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    public void testLargeSequenceWithSecurity() {
        try {
            sf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, Boolean.TRUE);
            URL url = getClass().getResource("test-sequence.xsd");
            Schema s = sf.newSchema(url);
            Validator v = s.newValidator();
            Assert.fail("Schema was accepted even with secure processing enabled.");
        } catch (SAXParseException e) {
            // falls through - exception expected
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

}
