/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package stream.XMLInputFactoryTest;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 8276050
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.XMLInputFactoryTest.InputFactoryTest
 * @summary Test XMLInputFactory functionalities.
 */
public class InputFactoryTest {
    @DataProvider(name = "AEProperties")
    public Object[][] getAEProperties() throws Exception {
        return new Object[][]{
            {XMLConstants.ACCESS_EXTERNAL_DTD, "all", "all"},
            {XMLConstants.ACCESS_EXTERNAL_SCHEMA, "all", "all"},
            {XMLConstants.ACCESS_EXTERNAL_DTD, "", ""},
            {XMLConstants.ACCESS_EXTERNAL_SCHEMA, "", ""},
        };
    }

    /*
     * Verifies that the XMLInputFactory returns security properties correctly.
    */
    @Test(dataProvider = "AEProperties")
    public void testProperty(String name, String value, String expected) {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        xif.setProperty(name, value);
        Assert.assertEquals(expected, (String)xif.getProperty(name));
    }
}
