/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package common.dtd;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.xml.sax.XMLReader;

/*
 * @test
 * @bug 8322214
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng common.dtd.DTDPropertiesTest
 * @summary Verifies the getProperty function on DTD properties works the same
 * as before the property 'jdk.xml.dtd.support' was introduced.
 */
public class DTDPropertiesTest {
    // Xerces Property
    public static final String DISALLOW_DTD = "http://apache.org/xml/features/disallow-doctype-decl";

    /*
     * DataProvider for verifying Xerces' disallow-DTD feature
     * Fields: property name, setting (null indicates not specified), expected
     */
    @DataProvider(name = "XercesProperty")
    public Object[][] getXercesProperty() throws Exception {
        return new Object[][] {
            { DISALLOW_DTD, null, false},
            { DISALLOW_DTD, true, true},
            { DISALLOW_DTD, false, false},
        };
    }

    /*
     * DataProvider for verifying StAX's supportDTD feature
     * Fields: property name, setting (null indicates not specified), expected
     */
    @DataProvider(name = "StAXProperty")
    public Object[][] getStAXProperty() throws Exception {
        return new Object[][] {
            { XMLInputFactory.SUPPORT_DTD, null, true},
            { XMLInputFactory.SUPPORT_DTD, true, true},
            { XMLInputFactory.SUPPORT_DTD, false, false},
        };
    }

    /**
     * Verifies the disallow DTD feature with SAX.
     *
     * @param name the name of the property
     * @param setting the setting of the property, null means not specified
     * @param expected the expected value
     * @throws Exception if the test fails
     */
    @Test(dataProvider = "XercesProperty")
    public void testSAX(String name, Boolean setting, Boolean expected) throws Exception {
        SAXParserFactory spf = SAXParserFactory.newDefaultInstance();
        if (setting != null) {
            spf.setFeature(name, setting);
        }
        Assert.assertEquals((Boolean)spf.getFeature(name), expected);
        System.out.println(spf.getFeature(name));


        SAXParser saxParser = spf.newSAXParser();
        XMLReader reader = saxParser.getXMLReader();
        Assert.assertEquals((Boolean)reader.getFeature(name), expected);
        System.out.println(reader.getFeature(name));
    }

    /**
     * Verifies the disallow DTD feature with DOM.
     *
     * @param name the name of the property
     * @param setting the setting of the property, null means not specified
     * @param expected the expected value
     * @throws Exception if the test fails
     */
    @Test(dataProvider = "XercesProperty")
    public void testDOM(String name, Boolean setting, Boolean expected) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
        if (setting != null) {
            dbf.setFeature(name, setting);
        }
        Assert.assertEquals((Boolean)dbf.getFeature(name), expected);
        System.out.println(dbf.getFeature(name));
    }

    /**
     * Verifies the StAX's supportDTD feature.
     *
     * @param name the name of the property
     * @param setting the setting of the property, null means not specified
     * @param expected the expected value
     * @throws Exception if the test fails
     */
    @Test(dataProvider = "StAXProperty")
    public void testStAX(String name, Boolean setting, Boolean expected) throws Exception {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        if (setting != null) {
            xif.setProperty(name, setting);
        }
        Assert.assertEquals((Boolean)xif.getProperty(name), expected);
        System.out.println((Boolean)xif.getProperty(name));
    }
}
