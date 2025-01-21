/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */
package org.xml.sax.ptests;

import static jaxp.library.JAXPTestUtilities.setSystemProperty;

import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Unit test for XMLReaderFactory.createXMLReader API.
 */
/*
 * @test
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm org.xml.sax.ptests.XMLReaderFactoryTest
 */
public class XMLReaderFactoryTest {
    /**
     * No exception expected when create XMLReader by default.
     * @throws org.xml.sax.SAXException when xml reader creation failed.
     */
    @Test
    public void createReader01() throws SAXException {
        assertNotNull(XMLReaderFactory.createXMLReader());
    }

    /**
     * No exception expected when create XMLReader with driver name
     * org.apache.xerces.parsers.SAXParser
     * or com.sun.org.apache.xerces.internal.parsers.SAXParser.
     * @throws org.xml.sax.SAXException when xml reader creation failed.
     */
    @Test
    public void createReader02() throws SAXException {
        setSystemProperty("org.xml.sax.driver",
            "com.sun.org.apache.xerces.internal.parsers.SAXParser");
        assertNotNull(XMLReaderFactory.
            createXMLReader("com.sun.org.apache.xerces.internal.parsers.SAXParser"));
    }

    /**
     * SAXException expected when create XMLReader with an invalid driver name.
     * @throws org.xml.sax.SAXException expected Exception
     */
    @Test(expectedExceptions = SAXException.class,
            expectedExceptionsMessageRegExp =
                    "SAX2 driver class org.apache.crimson.parser.ABCD not found")
    public void createReader03() throws SAXException{
        XMLReaderFactory.createXMLReader("org.apache.crimson.parser.ABCD");
    }
}
