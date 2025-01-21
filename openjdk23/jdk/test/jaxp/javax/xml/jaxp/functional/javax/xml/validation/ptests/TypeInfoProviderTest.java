/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */
package javax.xml.validation.ptests;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import static javax.xml.validation.ptests.ValidationTestConst.XML_DIR;
import static jaxp.library.JAXPTestUtilities.filenameToURL;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.TypeInfoProvider;
import javax.xml.validation.ValidatorHandler;

import org.testng.annotations.Test;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/*
 * @test
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm javax.xml.validation.ptests.TypeInfoProviderTest
 * @summary test ValidatorHandler.getTypeInfoProvider()
 */
public class TypeInfoProviderTest {

    private ValidatorHandler validatorHandler;

    @Test
    public void test() throws SAXException, ParserConfigurationException, IOException {

        SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File(XML_DIR + "shiporder11.xsd"));
        validatorHandler = schema.newValidatorHandler();
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        validatorHandler.setContentHandler(myDefaultHandler);

        InputSource is = new InputSource(filenameToURL(XML_DIR + "shiporder11.xml"));

        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        parserFactory.setNamespaceAware(true);
        XMLReader xmlReader = parserFactory.newSAXParser().getXMLReader();
        xmlReader.setContentHandler(validatorHandler);
        xmlReader.parse(is);

    }

    private class MyDefaultHandler extends DefaultHandler {

        public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
            TypeInfoProvider typeInfoProvider = validatorHandler.getTypeInfoProvider();
            int index = atts.getIndex("orderid");
            if (index != -1) {
                System.out.println(" Index " + index);
                System.out.println(" ElementType " + typeInfoProvider.getElementTypeInfo().getTypeName());
                assertEquals(typeInfoProvider.getAttributeTypeInfo(index).getTypeName(), "string");
                assertTrue(typeInfoProvider.isSpecified(index));
                assertFalse(typeInfoProvider.isIdAttribute(index));
            }

        }

    }
}
