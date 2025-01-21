/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package dom.ls;

import java.io.StringBufferInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.ls.LSSerializerFilter;
import org.w3c.dom.traversal.NodeFilter;

/*
 * @test
 * @bug 6376823
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm dom.ls.Bug6376823
 * @summary Test LSSerializer works.
 */
public class Bug6376823 {

    private static String XML_STRING = "<?xml version=\"1.0\"?><ROOT><ELEMENT1><CHILD1/><CHILD1><COC1/></CHILD1></ELEMENT1><ELEMENT2>test1<CHILD2/></ELEMENT2></ROOT>";
    private static DOMImplementationLS implLS;

    @Test
    public void testStringSourceWithXmlDecl() {
        String result = prepare(XML_STRING, true);
        System.out.println("testStringSource: output: " + result);
        Assert.assertTrue(result.indexOf("<?xml", 5) < 0, "XML Declaration expected in output");
    }

    private String prepare(String source, boolean xmlDeclFlag) {
        Document startDoc = null;
        DocumentBuilder domParser = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            domParser = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            Assert.fail("Exception occured: " + e.getMessage());
        }

        final StringBufferInputStream is = new StringBufferInputStream(XML_STRING);
        try {
            startDoc = domParser.parse(is);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occured: " + e.getMessage());
        }

        DOMImplementation impl = startDoc.getImplementation();
        implLS = (DOMImplementationLS) impl.getFeature("LS", "3.0");
        LSParser parser = implLS.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, "http://www.w3.org/2001/XMLSchema");

        LSInput src = getXmlSource(source);

        LSSerializer writer = implLS.createLSSerializer();

        DOMConfiguration conf = writer.getDomConfig();
        conf.setParameter("xml-declaration", Boolean.valueOf(xmlDeclFlag));

        // set filter
        writer.setFilter(new LSSerializerFilter() {
            public short acceptNode(Node enode) {
                return FILTER_ACCEPT;

            }

            public int getWhatToShow() {
                return NodeFilter.SHOW_ALL;
            }
        });

        Document doc = parser.parse(src);
        return writer.writeToString(doc);
    }

    private LSInput getXmlSource(String xml1) {
        LSInput src = implLS.createLSInput();
        try {
            src.setStringData(xml1);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occured: " + e.getMessage());
        }
        return src;
    }
}
