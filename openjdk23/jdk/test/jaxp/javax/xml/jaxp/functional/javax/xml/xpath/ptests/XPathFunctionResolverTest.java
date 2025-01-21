/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package javax.xml.xpath.ptests;

import static org.testng.Assert.assertEquals;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Class containing the test cases for XPathFunctionResolver.
 */
/*
 * @test
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm javax.xml.xpath.ptests.XPathFunctionResolverTest
 */
public class XPathFunctionResolverTest {
    /**
     * A XPath for evaluation environment and expressions.
     */
    private XPath xpath;

    /**
     * Create XPath object before every test. Make sure function resolver has
     * been set for XPath object.
     */
    @BeforeTest
    public void setup() {
        xpath = XPathFactory.newInstance().newXPath();
        if (xpath.getXPathFunctionResolver() == null) {
            xpath.setXPathFunctionResolver((functionName,arity) -> null);
        }
    }
    /**
     * Test for resolveFunction(QName functionName,int arity). evaluate will
     * continue as long as functionName is meaningful.
     *
     * @throws XPathExpressionException If the expression cannot be evaluated.
     */
    @Test
    public void testCheckXPathFunctionResolver01() throws XPathExpressionException {
        assertEquals(xpath.evaluate("round(1.7)", (Object)null), "2");
    }

    /**
     * Test for resolveFunction(QName functionName,int arity); evaluate throws
     * NPE if functionName  is null.
     *
     * @throws XPathExpressionException If the expression cannot be evaluated.
     */
    @Test(expectedExceptions = NullPointerException.class)
    public void testCheckXPathFunctionResolver02() throws XPathExpressionException {
        assertEquals(xpath.evaluate(null, "5"), "2");
    }
}
