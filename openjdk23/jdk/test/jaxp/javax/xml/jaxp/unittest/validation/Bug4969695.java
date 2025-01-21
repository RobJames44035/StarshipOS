/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation;

import javax.xml.validation.SchemaFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/*
 * @test
 * @bug 4969695
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.Bug4969695
 * @summary Test SchemaFactory.get/setProperty() throw NullPointerException
 * instead of SAXNotRecognizedException in case the "property name" parameter is null.
 */
public class Bug4969695 {

    SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

    @Test
    public void test01() throws SAXNotRecognizedException, SAXNotSupportedException {
        try {
            schemaFactory.getProperty(null);
            Assert.fail("exception expected");
        } catch (NullPointerException e) {
            ; // expected
        }
    }

    @Test
    public void test() throws SAXNotRecognizedException, SAXNotSupportedException {
        try {
            schemaFactory.setProperty(null, "123");
            Assert.fail("exception expected");
        } catch (NullPointerException e) {
            ; // as expected
        }
    }
}
