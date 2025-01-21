/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package common.config;

import javax.xml.stream.XMLInputFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @test @bug 8303530
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @modules java.xml/jdk.xml.internal
 * @run testng/othervm common.config.StAXPropertyTest0
 * @summary verifies that JAXP configuration file is customizable with a system
 * property "java.xml.config.file".
 * Note: this test is a duplicate of DOMPropertyTest. This test runs the
 * case with a custom configuration file only to avoid interfering with other
 * test cases.
 */
public class StAXPropertyTest0 extends ConfigurationTest {
    @Test(dataProvider = "getProperty0")
    public void testProperty(String config, String property, String expected) throws Exception {
        if (config != null) {
            System.setProperty(ConfigurationTest.SP_CONFIG, getPath(config));
        }
        XMLInputFactory xif = XMLInputFactory.newFactory();
        System.clearProperty(ConfigurationTest.SP_CONFIG);
        Assert.assertEquals(xif.getProperty(property), expected);
    }
}
