/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package datatype;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6320118
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm datatype.Bug6320118
 * @summary Test xml datatype XMLGregorianCalendar.
 */
public class Bug6320118 {

    DatatypeFactory df;

    @BeforeClass
    public void createDataTypeFactory() throws DatatypeConfigurationException {
        df = DatatypeFactory.newInstance();
    }

    @Test
    public void test1() {
        XMLGregorianCalendar calendar = df.newXMLGregorianCalendar(1970, 1, 1, 24, 0, 0, 0, 0);
        Assert.assertEquals(calendar.getYear(), 1970);
        Assert.assertEquals(calendar.getMonth(), 1);
        Assert.assertEquals(calendar.getDay(), 2);
        Assert.assertEquals(calendar.getHour(), 0, "hour 24 needs to be treated as hour 0 of next day");
    }

    @Test
    public void test2() {
        XMLGregorianCalendar calendar = df.newXMLGregorianCalendarTime(24, 0, 0, 0);
        Assert.assertEquals(calendar.getHour(), 0, "hour 24 needs to be treated as hour 0 of next day");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test3() {
        XMLGregorianCalendar calendar = df.newXMLGregorianCalendar();
        // Must fail as other params are not 0 but undefined
        calendar.setHour(24);
    }

    @Test
    public void test4() {
        XMLGregorianCalendar calendar = df.newXMLGregorianCalendar();
        calendar.setTime(24, 0, 0, 0);
        Assert.assertEquals(calendar.getHour(), 0, "hour 24 needs to be treated as hour 0 of next day");
    }

}
