/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package common.config;

import javax.xml.transform.TransformerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @test @bug 8303530
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @modules java.xml/jdk.xml.internal
 * @run testng/othervm common.config.TransformerPropertyTest
 * @summary verifies that JAXP configuration file is customizable with a system
 * property "java.xml.config.file".
 */
public class TransformerPropertyTest extends ConfigurationTest {
    @Test(dataProvider = "getProperty")
    public void testProperty(String config, String property, String expected) throws Exception {
        if (config != null) {
            System.setProperty(ConfigurationTest.SP_CONFIG, getPath(config));
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        System.clearProperty(ConfigurationTest.SP_CONFIG);
        Assert.assertEquals(tf.getAttribute(property), expected);
    }
}
