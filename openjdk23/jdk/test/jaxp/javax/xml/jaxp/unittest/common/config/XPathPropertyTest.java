/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package common.config;

import javax.xml.xpath.XPathFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @test @bug 8303530
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @modules java.xml/jdk.xml.internal
 * @run testng/othervm common.config.XPathPropertyTest
 * @summary verifies that JAXP configuration file is customizable with a system
 * property "java.xml.config.file".
 */
public class XPathPropertyTest extends ConfigurationTest {
   /*
     * DataProvider for testing the configuration file and system property.
     *
     * Fields:
     *     configuration file, property name, property value
     */
    @DataProvider(name = "getProperty")
    public Object[][] getProperty() {

        return new Object[][]{
            {null, "jdk.xml.xpathExprOpLimit", "100"},
        };
    }

    @Test(dataProvider = "getProperty")
    public void testProperty(String config, String property, String expected) throws Exception {
        if (config != null) {
            System.setProperty(ConfigurationTest.SP_CONFIG, getPath(config));
        }
        XPathFactory xf = XPathFactory.newInstance();
        System.clearProperty(ConfigurationTest.SP_CONFIG);
        Assert.assertEquals(xf.getProperty(property), expected);
    }
}
