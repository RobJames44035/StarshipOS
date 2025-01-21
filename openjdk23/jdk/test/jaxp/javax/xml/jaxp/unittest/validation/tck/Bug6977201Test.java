/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation.tck;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6977201
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.tck.Bug6977201Test
 * @summary Test Validator interprets regex "" correctly.
 */
public class Bug6977201Test {
    static final String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

    SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

    @Test
    public void test() {
        validate(Bug6977201Test.class.getResource("Bug6977201/reA2.xsd").getPath(), Bug6977201Test.class.getResource("Bug6977201/reA2.xml").getPath());
        validate(Bug6977201Test.class.getResource("Bug6977201/reA3.xsd").getPath(), Bug6977201Test.class.getResource("Bug6977201/reA3.xml").getPath());
        validate(Bug6977201Test.class.getResource("Bug6977201/reA4.xsd").getPath(), Bug6977201Test.class.getResource("Bug6977201/reA4.xml").getPath());
        validate(Bug6977201Test.class.getResource("Bug6977201/reA5.xsd").getPath(), Bug6977201Test.class.getResource("Bug6977201/reA5.xml").getPath());
        validate(Bug6977201Test.class.getResource("Bug6977201/reA6.xsd").getPath(), Bug6977201Test.class.getResource("Bug6977201/reA6.xml").getPath());
    }

    // JCK negative test
    public void validate(String xsd, String xml) {
        try {
            Schema schema = schemaFactory.newSchema(new StreamSource(xsd));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
            Assert.fail("should report error");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // e.printStackTrace();
        }
    }

}
