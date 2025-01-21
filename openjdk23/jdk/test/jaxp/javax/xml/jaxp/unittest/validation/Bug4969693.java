/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation;

import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

/*
 * @test
 * @bug 4969693
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.Bug4969693
 * @summary Test Validator.get/setProperty() throw NullPointerException
 * instead of SAXNotRecognizedException in case the "property name" parameter is null.
 */
public class Bug4969693 {

    SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

    @Test
    public void test01() throws SAXException {
        Validator validator = schemaFactory.newSchema().newValidator();
        try {
            validator.getProperty(null);
            Assert.fail("exception expected");
        } catch (NullPointerException e) {
            ;
        }
    }

    @Test
    public void test02() throws SAXException {
        Validator validator = schemaFactory.newSchema().newValidator();
        try {
            validator.setProperty(null, "abc");
            Assert.fail("exception expected");
        } catch (NullPointerException e) {
            ;
        }
    }
}
