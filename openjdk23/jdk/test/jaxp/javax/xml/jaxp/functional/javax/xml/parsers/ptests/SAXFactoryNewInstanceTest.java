/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package javax.xml.parsers.ptests;

import static org.testng.Assert.assertNotNull;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import jaxp.library.JAXPDataProvider;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

/*
 * @test
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm javax.xml.parsers.ptests.SAXFactoryNewInstanceTest
 * @summary Tests for SAXParserFactory.newInstance(factoryClassName , classLoader)
 */
public class SAXFactoryNewInstanceTest {

    private static final String SAXPARSER_FACTORY_CLASSNAME = "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl";

    @DataProvider(name = "parameters")
    public Object[][] getValidateParameters() {
        return new Object[][] { { SAXPARSER_FACTORY_CLASSNAME, null }, { SAXPARSER_FACTORY_CLASSNAME, this.getClass().getClassLoader() } };
    }

    /*
     * test for SAXParserFactory.newInstance(java.lang.String factoryClassName,
     * java.lang.ClassLoader classLoader) factoryClassName points to correct
     * implementation of javax.xml.parsers.SAXParserFactory , should return
     * newInstance of SAXParserFactory
     */
    @Test(dataProvider = "parameters")
    public void testNewInstance(String factoryClassName, ClassLoader classLoader) throws ParserConfigurationException, SAXException {
        SAXParserFactory spf = SAXParserFactory.newInstance(factoryClassName, classLoader);
        SAXParser sp = spf.newSAXParser();
        assertNotNull(sp);
    }

    /*
     * test for SAXParserFactory.newInstance(java.lang.String factoryClassName,
     * java.lang.ClassLoader classLoader) factoryClassName is null , should
     * throw FactoryConfigurationError
     */
    @Test(expectedExceptions = FactoryConfigurationError.class, dataProvider = "new-instance-neg", dataProviderClass = JAXPDataProvider.class)
    public void testNewInstanceNeg(String factoryClassName, ClassLoader classLoader) {
        SAXParserFactory.newInstance(factoryClassName, classLoader);
    }

}
