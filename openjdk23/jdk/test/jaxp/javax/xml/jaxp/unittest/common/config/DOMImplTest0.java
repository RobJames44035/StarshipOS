/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package common.config;

import static common.config.ConfigurationTest.SP_CONFIG;
import static common.config.ConfigurationTest.getPath;
import javax.xml.parsers.DocumentBuilderFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @test @bug 8303530
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @modules java.xml/jdk.xml.internal
 * @run testng/othervm common.config.DOMImplTest0
 * @summary the tests with the default and custom configurations files have to be
 * separate because they both are loaded once.
 */
public class DOMImplTest0 {
    /*
     * DataProvider for testing the configuration file and system property.
     *
     * Fields:
     *     configuration file, factory implementation class
     */
    @DataProvider(name = "getImpl")
    public Object[][] getImpl() {

        return new Object[][]{
            {null, "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl"},
        };
    }

    @Test(dataProvider = "getImpl")
    public void testDOMImpl(String config, String expected) throws Exception {
        if (config != null) {
            System.setProperty(SP_CONFIG, getPath(config));
        }
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        System.clearProperty(SP_CONFIG);
        Assert.assertEquals(dbf.getClass().getName(), expected);
    }
}
