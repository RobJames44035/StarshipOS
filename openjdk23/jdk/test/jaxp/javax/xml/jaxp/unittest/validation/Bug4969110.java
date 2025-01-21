/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation;

import javax.xml.validation.SchemaFactory;
import javax.xml.validation.ValidatorHandler;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;

/*
 * @test
 * @bug 4969110
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.Bug4969110
 * @summary Test ValidationHandler.set/getProperty() throws a correct exception
 * instead of a sun internal exception in case the "property name" parameter is invalid.
 */
public class Bug4969110 {

    SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

    @Test
    public void test1() throws SAXException {
        try {
            ValidatorHandler validatorHandler = schemaFactory.newSchema().newValidatorHandler();
            validatorHandler.getProperty("unknown1234");
            Assert.fail("SAXNotRecognizedException was not thrown.");
        } catch (SAXNotRecognizedException e) {
        }
    }

    @Test
    public void test2() throws SAXException {
        try {
            doTest(null);
            Assert.fail("NullPointerException was not thrown.");
        } catch (NullPointerException e) {
        }
    }

    @Test
    public void test3() throws SAXException {
        try {
            doTest("unknown1234");
            Assert.fail("SAXNotRecognizedException was not thrown.");
        } catch (SAXNotRecognizedException e) {
        }
    }

    public void doTest(String name) throws SAXException {
        ValidatorHandler validatorHandler = schemaFactory.newSchema().newValidatorHandler();
        validatorHandler.setProperty(name, "123");
    }
}
