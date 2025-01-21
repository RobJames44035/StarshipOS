/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time.temporal;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class TestChronoField {
    Map<ChronoField, String> fieldMap;


    @BeforeClass
    public void initClass() {
        fieldMap = new HashMap<>();
        fieldMap.put(ChronoField.ERA, "era");
        fieldMap.put(ChronoField.YEAR, "year");
        fieldMap.put(ChronoField.MONTH_OF_YEAR, "month");
        fieldMap.put(ChronoField.DAY_OF_MONTH, "day");
        fieldMap.put(ChronoField.AMPM_OF_DAY, "dayperiod");
        fieldMap.put(ChronoField.ALIGNED_WEEK_OF_YEAR, "week");
        fieldMap.put(ChronoField.DAY_OF_WEEK, "weekday");
        fieldMap.put(ChronoField.HOUR_OF_DAY, "hour");
        fieldMap.put(ChronoField.MINUTE_OF_HOUR, "minute");
        fieldMap.put(ChronoField.SECOND_OF_MINUTE, "second");
        fieldMap.put(ChronoField.OFFSET_SECONDS, "zone");
    }

    @DataProvider(name = "localeList")
    Locale[] data_localeList() {
        return new Locale[] {
                Locale.US,
                Locale.GERMAN,
                Locale.JAPAN,
                Locale.ROOT,
        };
    }
    //-----------------------------------------------------------------------
    @DataProvider(name = "localeDisplayNames")
    Object[][] data_localeDisplayNames() {
        return new Object[][] {
                {ChronoField.ERA},
                {ChronoField.YEAR},
                {ChronoField.MONTH_OF_YEAR},
                {ChronoField.DAY_OF_WEEK},
                // {ChronoField.ALIGNED_WEEK_OF_YEAR},
                {ChronoField.DAY_OF_MONTH},
                {ChronoField.AMPM_OF_DAY},
                {ChronoField.HOUR_OF_DAY},
                {ChronoField.MINUTE_OF_HOUR},
                {ChronoField.SECOND_OF_MINUTE},
        };
    }

    @Test
    public void test_IsoFields_week_based_year() {
        Locale locale = Locale.US;
        String name = IsoFields.WEEK_OF_WEEK_BASED_YEAR.getDisplayName(locale);
        assertEquals(name, "week");
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void test_nullIsoFields_week_based_year() {
        String name = IsoFields.WEEK_OF_WEEK_BASED_YEAR.getDisplayName((Locale)null);
    }

    @Test
    public void test_WeekFields_week_based_year() {
        Locale locale = Locale.US;
        TemporalField weekOfYearField = WeekFields.SUNDAY_START.weekOfYear();
        String name = weekOfYearField.getDisplayName(locale);
        assertEquals(name, "week");
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void test_nullWeekFields_week_based_year() {
        TemporalField weekOfYearField = WeekFields.SUNDAY_START.weekOfYear();
        String name = weekOfYearField.getDisplayName((Locale)null);
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void test_nullLocaleChronoFieldDisplayName() {
        ChronoField.YEAR.getDisplayName((Locale)null);
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void test_nullLocaleTemporalFieldDisplayName() {
        // Test the default method in TemporalField using the
        // IsoFields.DAY_OF_QUARTER which does not override getDisplayName
        IsoFields.DAY_OF_QUARTER.getDisplayName((Locale)null);
    }
}
