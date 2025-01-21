/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package sax;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/*
 * @test
 * @bug 6992561
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm sax.Bug6992561Test
 * @summary Test encoding of SystemId in Locator.
 */
public class Bug6992561Test {

    @Test
    public void test() {
        ContentHandler handler = new DefaultHandler() {
            public void setDocumentLocator(Locator locator) {
                String sysId = locator.getSystemId();
                System.out.println(locator.getSystemId());
                if (sysId.indexOf("%7") > 0) {
                    Assert.fail("the original system id should be left as is and not encoded.");
                }
            }
        };

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser parser;
        try {
            parser = spf.newSAXParser();

            XMLReader reader = parser.getXMLReader();
            reader.setContentHandler(handler);
            String xml = "<test>abc</test>";
            ByteArrayInputStream bis = new ByteArrayInputStream(xml.getBytes());
            InputSource is = new InputSource("file:/home2/ramapulavarthi/w/bugs/jaxws861/foo~bla/test/src/wsdl/HelloTypes.xsd");
            is.setByteStream(bis);
            reader.parse(is);

        } catch (ParserConfigurationException ex) {
            Assert.fail(ex.toString());
        } catch (SAXException ex) {
            Assert.fail(ex.toString());
        } catch (IOException ex) {
            Assert.fail(ex.toString());
        }
    }

}
