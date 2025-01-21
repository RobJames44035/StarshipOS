/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test
 * @bug 4028518
 * @summary Ensure cloned GregorianCalendar is unchanged when modifying its original.
 * @run junit bug4028518
 */

import java.util.GregorianCalendar;

import static java.util.Calendar.DAY_OF_MONTH;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class bug4028518 {

    /*
     * Ensure modifying the original GregorianCalendar does not
     * modify the cloned one as well
     */
    @Test
    public void clonedShouldNotChangeOriginalTest() {
        GregorianCalendar cal1 = new GregorianCalendar() ;
        GregorianCalendar cal2 = (GregorianCalendar) cal1.clone() ;
        cal1.add(DAY_OF_MONTH, 1) ;
        assertNotEquals(cal1.get(DAY_OF_MONTH), cal2.get(DAY_OF_MONTH),
                "Cloned calendar should not have same value as original");
    }
}
