/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package common.config;

import static common.config.ConfigurationTest.getPath;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @test @bug 8303530
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @modules java.xml/jdk.xml.internal
 * @run testng/othervm common.config.TransformerFactoryTest
 * @summary verifies that JAXP configuration file is customizable with a system
 * property "java.xml.config.file".
 */
public class TransformerFactoryTest extends TransformerFactory {
    @DataProvider(name = "getImpl")
    public Object[][] getImpl() {

        return new Object[][]{
            {null, "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl"},
        };
    }

    @Test(dataProvider = "getImpl")
    public void testFactory(String config, String expected) throws Exception {
        if (config != null) {
            System.setProperty(ConfigurationTest.SP_CONFIG, getPath(config));
        }

        TransformerFactory tf = TransformerFactory.newInstance();
        System.clearProperty(ConfigurationTest.SP_CONFIG);
        Assert.assertEquals(tf.getClass().getName(), expected);
    }

    @Override
    public Transformer newTransformer(Source source) throws TransformerConfigurationException {
        return null;
    }

    @Override
    public Transformer newTransformer() throws TransformerConfigurationException {
        return null;
    }

    @Override
    public Templates newTemplates(Source source) throws TransformerConfigurationException {
        return null;
    }

    @Override
    public Source getAssociatedStylesheet(Source source, String media, String title, String charset) throws TransformerConfigurationException {
        return null;
    }

    @Override
    public void setURIResolver(URIResolver resolver) {
        // do nothing
    }

    @Override
    public URIResolver getURIResolver() {
        return null;
    }

    @Override
    public void setFeature(String name, boolean value) throws TransformerConfigurationException {
        // do nothing
    }

    @Override
    public boolean getFeature(String name) {
        return false;
    }

    @Override
    public void setAttribute(String name, Object value) {
        // do nothing
    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

    @Override
    public void setErrorListener(ErrorListener listener) {
        // do nothing
    }

    @Override
    public ErrorListener getErrorListener() {
        return null;
    }

}
