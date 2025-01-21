/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package javax.xml.parsers.ptests;

import static jaxp.library.JAXPTestUtilities.setSystemProperty;
import static jaxp.library.JAXPTestUtilities.clearSystemProperty;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.SAXParserFactory;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Class containing the test cases for SAXParserFactory/DocumentBuilderFactory
 * newInstance methods.
 */
/*
 * @test
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm javax.xml.parsers.ptests.FactoryConfErrorTest
 */
public class FactoryConfErrorTest {

    /**
     * Set properties DocumentBuilderFactory and SAXParserFactory to invalid
     * value before any test run.
     */
    @BeforeTest
    public void setup() {
        setSystemProperty("javax.xml.parsers.DocumentBuilderFactory", "xx");
        setSystemProperty("javax.xml.parsers.SAXParserFactory", "xx");
    }

    /**
     * Restore properties DocumentBuilderFactory and SAXParserFactory to default
     * value after all tests run.
     */
    @AfterTest
    public void cleanup() {
        clearSystemProperty("javax.xml.parsers.DocumentBuilderFactory");
        clearSystemProperty("javax.xml.parsers.SAXParserFactory");
    }

    /**
     * To test exception thrown if javax.xml.parsers.SAXParserFactory property
     * is invalid.
     */
    @Test(expectedExceptions = FactoryConfigurationError.class)
    public void testNewInstance01() {
        SAXParserFactory.newInstance();
    }

    /**
     * To test exception thrown if javax.xml.parsers.DocumentBuilderFactory is
     * invalid.
     */
    @Test(expectedExceptions = FactoryConfigurationError.class)
    public void testNewInstance02() {
        DocumentBuilderFactory.newInstance();
    }
}
