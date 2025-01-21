/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package datatype;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 7042647
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm datatype.Bug7042647Test
 * @summary Test getFirstDayOfWeek is correct after converting XMLGregorianCalendar to a GregorianCalendar.
 */
public class Bug7042647Test {

    @Test
    public void test() throws DatatypeConfigurationException {
        XMLGregorianCalendar xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(1970, 1, 1, 0, 0, 0, 0, 0);
        GregorianCalendar calendar = xmlCalendar.toGregorianCalendar();
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        Calendar defaultCalendar = Calendar.getInstance();
        int defaultFirstDayOfWeek = defaultCalendar.getFirstDayOfWeek();
        if (firstDayOfWeek != defaultFirstDayOfWeek) {
            Assert.fail("Failed firstDayOfWeek=" + firstDayOfWeek + " != defaultFirstDayOfWeek=" + defaultFirstDayOfWeek);
        } else {
            System.out.println("Success firstDayOfWeek=" + firstDayOfWeek + " == defaultFirstDayOfWeek=" + defaultFirstDayOfWeek);
        }
    }

}
