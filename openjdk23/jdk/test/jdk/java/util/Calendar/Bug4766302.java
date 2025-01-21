/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4766302
 * @summary Make sure that calling computeTime doesn't reset the isTimeSet value.
 * @run junit Bug4766302
 */

import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Bug4766302 {

    // Extend GregorianCalendar to check the protected value of isTimeSet
    @SuppressWarnings("serial")
    static class MyCalendar extends GregorianCalendar {
        boolean isTimeStillSet() {
            return isTimeSet;
        }

        protected void computeTime() {
            super.computeTime();
        }
    }

    // Check the value of isTimeStillSet() after calling computeTime()
    @Test
    public void validateIsTimeSetTest() {
        MyCalendar cal = new MyCalendar();
        cal.computeTime();
        assertTrue(cal.isTimeStillSet(), "computeTime() call reset isTimeSet.");
    }
}
