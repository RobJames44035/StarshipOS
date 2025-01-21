/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package xpath;

import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.InputSource;

/*
 * @test
 * @bug 4992793
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm xpath.Bug4992793
 * @summary Test XPath.evaluate(expression,source,returnType) throws NPE if expression is null.
 */
public class Bug4992793 {


    // test for XPath.evaluate(java.lang.String expression, InputSource source)
    // - default returnType is String
    // source is null , should throw NPE
    @Test
    public void testXPath24() throws Exception {
        try {
            createXPath().evaluate(null, new InputSource(new StringReader("<root/>")));
            Assert.fail();
        } catch (NullPointerException e) {
            ; // as expected
        }
    }

    // test for XPath.evaluate(java.lang.String expression, InputSource source,
    // QName returnType)
    // source is null , should throw NPE
    @Test
    public void testXPath29() throws Exception {
        try {
            createXPath().evaluate(null, new InputSource(new StringReader("<root/>")), XPathConstants.STRING);
            Assert.fail();
        } catch (NullPointerException e) {
            ; // as expected
        }
    }

    private XPath createXPath() throws XPathFactoryConfigurationException {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        Assert.assertNotNull(xpathFactory);
        XPath xpath = xpathFactory.newXPath();
        Assert.assertNotNull(xpath);
        return xpath;
    }
}
