/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package parsers;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/*
 * @test
 * @bug 6518733
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm parsers.Bug6760982
 * @summary Test SAX parser handles several attributes with containing "&gt;".
 */
public class Bug6760982 {

    @Test
    public void test() {
        try {
            Document xmlDoc = _Parse(new File(getClass().getResource("bug6760982.xml").getFile()));
            Node node = xmlDoc.getDocumentElement();

            _ProcessNode(node, 0);
            _Flush();
        } catch (Exception e) {
            _ErrPrintln("Exception: " + e.toString());
            Assert.fail("Exception: " + e.getMessage());
        }
    }

    private static void _Flush() {
        System.out.flush();
        System.err.flush();
    }

    private static void _Println(String str, int level) {
        for (int i = 0; i < level; i++)
            System.out.print("    ");

        System.out.println(str);
        System.out.flush();
    }

    private static void _ErrPrintln(String aStr) {
        System.out.flush();
        System.err.println(aStr);
        System.err.flush();
    }

    private static Document _Parse(File f) throws Exception {
        FileReader rd = new FileReader(f);
        Document doc = _Parse(rd);

        rd.close();

        return doc;
    }

    private static Document _Parse(Reader src) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setValidating(false); // to improve performance

        DocumentBuilder xmlParser = dbf.newDocumentBuilder();
        InputSource is = new InputSource(src);

        return xmlParser.parse(is);
    }

    private static void _PrintAttributes(Node n, int level) {
        NamedNodeMap nnmap = n.getAttributes();

        if (nnmap != null && nnmap.getLength() > 0) {
            _Println("<attribs> (" + nnmap.getClass() + "):", level + 1);

            for (int i = 0; i < nnmap.getLength(); i++) {
                Node an = nnmap.item(i);

                String nameStr = an.getNodeName();
                String valueStr = an.getNodeValue();

                if (valueStr != "")
                    nameStr += " = " + valueStr;

                _Println(nameStr, level + 2);
            }
        }
    }

    private static void _ProcessChildren(Node n, int level) throws Exception {
        NodeList nlist = n.getChildNodes();

        if (nlist != null)
            for (int i = 0; i < nlist.getLength(); i++)
                _ProcessNode(nlist.item(i), level + 1);
    }

    private static void _ProcessNode(Node n, int level) throws Exception {
        n.getAttributes();
        n.getChildNodes();

        // At this point, for JVM 1.6 and Xerces <= 1.3.1,
        // Test-XML.xml::mytest:Y's attribute is (already) bad.

        switch (n.getNodeType()) {

            case Node.TEXT_NODE:
                String str = n.getNodeValue().trim();

                /* ...Only print non-empty strings... */
                if (str.length() > 0) {
                    String valStr = n.getNodeValue();

                    _Println(valStr, level);
                }
                break;

            case Node.COMMENT_NODE:
                break;

            default: {
                String nodeNameStr = n.getNodeName();

                _Println(nodeNameStr + " (" + n.getClass() + "):", level);

                /* ...Print children... */
                _ProcessChildren(n, level);

                /* ...Print optional node attributes... */
                _PrintAttributes(n, level);
            }
        }
    }
}
