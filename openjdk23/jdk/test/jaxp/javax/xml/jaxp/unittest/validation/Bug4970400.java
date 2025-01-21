/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation;

import javax.xml.validation.SchemaFactory;
import javax.xml.validation.ValidatorHandler;

import org.testng.annotations.Test;

/*
 * @test
 * @bug 4970400
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.Bug4970400
 * @summary Test ValidatorHandler recognizes namespace-prefixes feature.
 */
public class Bug4970400 {

    @Test
    public void test1() throws Exception {
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        ValidatorHandler validatorHandler = schemaFactory.newSchema().newValidatorHandler();
        validatorHandler.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
        validatorHandler.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
    }
}
