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
 * @bug 4971607
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.Bug4971607
 * @summary Test ValidatorHandler.getFeature(...) throws NullPointerException when name parameter is null.
 */
public class Bug4971607 {

    @Test
    public void test1() throws Exception {
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

        ValidatorHandler validatorHandler = schemaFactory.newSchema().newValidatorHandler();

        try {
            validatorHandler.getFeature(null);
            Assert.fail();
        } catch (NullPointerException e) {
            e.printStackTrace();
            ; // as expected
        }
    }
}
