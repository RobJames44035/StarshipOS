/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package xpath;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import javax.xml.xpath.XPathFunctionResolver;
import javax.xml.xpath.XPathVariableResolver;

/**
 * A dummy implementation of the XPathFactory without implementing
 * the setProperty/getProperty methods
 */
public class XPathFactoryDummyImpl extends XPathFactory {

    @Override
        public boolean isObjectModelSupported(String objectModel) {
            // support the default object model, W3C DOM
            if (objectModel.equals(XPathFactory.DEFAULT_OBJECT_MODEL_URI)) {
                return true;
            }

            // no support
            return false;
        }


    @Override
    public void setFeature(String name, boolean value) throws XPathFactoryConfigurationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean getFeature(String name) throws XPathFactoryConfigurationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setXPathVariableResolver(XPathVariableResolver resolver) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setXPathFunctionResolver(XPathFunctionResolver resolver) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public XPath newXPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
