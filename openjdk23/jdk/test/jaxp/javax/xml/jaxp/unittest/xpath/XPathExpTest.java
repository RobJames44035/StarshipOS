/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package xpath;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * @test
 * @bug 8284920
 * @run testng/othervm xpath.XPathExpTest
 * @summary Tests for various XPath Expressions.
 */
public class XPathExpTest {

    private static final String XML =
              "<root>"
            + "   <child id='1'>"
            + "       <grandchild id='1'/>"
            + "       <grandchild id='2'/>"
            + "   </child>"
            + "   <child id='2'>"
            + "       <grandchild id='3'/>"
            + "       <grandchild id='4'/>"
            + "   </child>"
            + "   <child id='3'>"
            + "       <grandchild id='5'/>"
            + "       <grandchild id='6'/>"
            + "   </child>"
            + " </root>";
    private static final String PARENT_CHILD = "child(2)";

    /*
     * DataProvider for XPath expression test.
     * Data columns:
     *  see parameters of the test "test"
     */
    @DataProvider(name = "xpathExp")
    public Object[][] getXPathExpression() throws Exception {

        return new Object[][]{
            // verifies various forms of the parent axis
            {"/root/child[@id='2']", PARENT_CHILD},
            {"//grandchild[@id='3']/parent::child", PARENT_CHILD},
            {"//grandchild[@id='3']/parent::node()", PARENT_CHILD},
            {"//grandchild[@id='3']/parent::*", PARENT_CHILD},
            {"//grandchild[@id='3']/parent::node()/grandchild[@id='4']/parent::node()", PARENT_CHILD},
            {"//grandchild[@id='3']/..", PARENT_CHILD},
            {"//grandchild[@id='3']/../grandchild[@id='4']/..", PARENT_CHILD},
            {"//grandchild[@id='3']/parent::node()/grandchild[@id='4']/..", PARENT_CHILD},

            // verifies various forms of the self axis
            {"/root/child[@id='2']/self::child", PARENT_CHILD},
            {"/root/child[@id='2']/self::node()", PARENT_CHILD},
            {"/root/child[@id='2']/self::*", PARENT_CHILD},
            {"self::node()/root/child[@id='2']", PARENT_CHILD},
            {"/root/child[@id='2']/.", PARENT_CHILD},
            {"./root/child[@id='2']", PARENT_CHILD},
            {".//child[@id='2']", PARENT_CHILD},
            {"//grandchild[@id='3']/./../grandchild[@id='4']/..", PARENT_CHILD},
            {"//grandchild[@id='3']/./parent::node()/grandchild[@id='4']/..", PARENT_CHILD},
        };
    }

    /**
     * Verifies XPath expressions.
     *
     * @param exp XPath expression
     * @param expected expected result
     * @throws Exception
     */
    @Test(dataProvider = "xpathExp")
    void test(String exp, String expected) throws Exception {
        Document doc = getDoc(XML);
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nl = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);
        Node child = nl.item(0);
        Assert.assertEquals(
                child.getNodeName() + "(" + child.getAttributes().item(0).getNodeValue() + ")",
                expected);
    }

    Document getDoc(String xml) throws Exception {
        DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
        dfactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = dfactory.newDocumentBuilder();
        InputStream is = new ByteArrayInputStream(xml.getBytes());
        return docBuilder.parse(is);
    }
}
