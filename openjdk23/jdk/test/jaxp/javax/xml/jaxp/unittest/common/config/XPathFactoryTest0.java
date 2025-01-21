/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package common.config;

import static common.config.ConfigurationTest.getPath;
import javax.xml.xpath.XPathFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @test @bug 8303530
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @modules java.xml/jdk.xml.internal
 * @run testng/othervm common.config.XPathFactoryTest0
 * @summary verifies that JAXP configuration file is customizable with a system
 * property "java.xml.config.file".
 */
public class XPathFactoryTest0 {
    @DataProvider(name = "getImpl")
    public Object[][] getImpl() {

        return new Object[][]{
            {null, "com.sun.org.apache.xpath.internal.jaxp.XPathFactoryImpl"},
        };
    }

    @Test(dataProvider = "getImpl")
    public void testFactory(String config, String expected) throws Exception {
        if (config != null) {
            System.setProperty(ConfigurationTest.SP_CONFIG, getPath(config));
        }

        XPathFactory xf = XPathFactory.newInstance();
        System.clearProperty(ConfigurationTest.SP_CONFIG);
        Assert.assertEquals(xf.getClass().getName(), expected);
    }
}
