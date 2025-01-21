/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package common.config;

import static common.config.ConfigurationTest.getPath;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import javax.xml.xpath.XPathFunctionResolver;
import javax.xml.xpath.XPathVariableResolver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @test @bug 8303530
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @modules java.xml/jdk.xml.internal
 * @run testng/othervm common.config.XPathFactoryTest
 * @summary verifies that JAXP configuration file is customizable with a system
 * property "java.xml.config.file".
 */
public class XPathFactoryTest extends XPathFactory {
    @DataProvider(name = "getImpl")
    public Object[][] getImpl() {

        return new Object[][]{
            {"jaxpImpls.properties", "common.config.XPathFactoryTest"},
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

    @Override
    public boolean isObjectModelSupported(String objectModel) {
        return false;
    }

    @Override
    public void setFeature(String name, boolean value) throws XPathFactoryConfigurationException {
        // do nothing
    }

    @Override
    public boolean getFeature(String name) throws XPathFactoryConfigurationException {
        return false;
    }

    @Override
    public void setXPathVariableResolver(XPathVariableResolver resolver) {
        // do nothing
    }

    @Override
    public void setXPathFunctionResolver(XPathFunctionResolver resolver) {
        // do nothing
    }

    @Override
    public XPath newXPath() {
        return null;
    }

}
