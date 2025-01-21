/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package javax.xml.transform.ptests.othervm;

import static jaxp.library.JAXPTestUtilities.setSystemProperty;

import static org.testng.Assert.fail;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.testng.annotations.Test;

/**
 * Negative test for set invalid TransformerFactory property.
 */
/*
 * @test
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm javax.xml.transform.ptests.othervm.TFCErrorTest
 */
public class TFCErrorTest {
    @Test(expectedExceptions = ClassNotFoundException.class)
    public void tfce01() throws Exception {
        try{
            setSystemProperty("javax.xml.transform.TransformerFactory","xx");
            TransformerFactory.newInstance();
            fail("Expect TransformerFactoryConfigurationError here");
        } catch (TransformerFactoryConfigurationError expected) {
            throw expected.getException();
        }
    }
}
