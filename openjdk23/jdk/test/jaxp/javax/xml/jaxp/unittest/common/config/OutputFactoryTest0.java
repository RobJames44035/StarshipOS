/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package common.config;

import static common.config.ConfigurationTest.getPath;
import javax.xml.stream.XMLOutputFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @test @bug 8303530
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @modules java.xml/jdk.xml.internal
 * @run testng/othervm common.config.OutputFactoryTest0
 * @summary the tests with the default and custom configurations files have to be
 * separate because they both are loaded once.
 */
public class OutputFactoryTest0 {
    @DataProvider(name = "getImpl")
    public Object[][] getImpl() {

        return new Object[][]{
            {null, "com.sun.xml.internal.stream.XMLOutputFactoryImpl"},
        };
    }

    @Test(dataProvider = "getImpl")
    public void testOutputFactory(String config, String expected) throws Exception {
        if (config != null) {
            System.setProperty(ConfigurationTest.SP_CONFIG, getPath(config));
        }

        XMLOutputFactory xof = XMLOutputFactory.newFactory();
        System.clearProperty(ConfigurationTest.SP_CONFIG);
        Assert.assertEquals(xof.getClass().getName(), expected);
    }
}
