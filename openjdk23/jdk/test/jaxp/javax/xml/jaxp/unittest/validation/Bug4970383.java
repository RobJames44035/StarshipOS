/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation;

import javax.xml.validation.SchemaFactory;
import javax.xml.validation.ValidatorHandler;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 4970383
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.Bug4970383
 * @summary Test validatorHandler.setFeature throws NullPointerException if name parameter is null.
 */
public class Bug4970383 {

    @Test
    public void test() throws Exception {
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        ValidatorHandler validatorHandler = schemaFactory.newSchema().newValidatorHandler();
        try {
            validatorHandler.setFeature(null, false);
            Assert.fail("should report an error");
        } catch (NullPointerException e) {
            ; // expected
        }
    }
}
