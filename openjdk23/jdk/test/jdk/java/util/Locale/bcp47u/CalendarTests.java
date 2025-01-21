/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 *
 * @test
 * @bug 8176841
 * @summary Tests Calendar class deals with Unicode extensions
 *      correctly.
 * @modules jdk.localedata
 * @run testng/othervm CalendarTests
 */

import static org.testng.Assert.assertEquals;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test Calendar with BCP47 U extensions
 */
@Test
public class CalendarTests {
    private static TimeZone defaultTZ;

    private static final TimeZone ASIATOKYO = TimeZone.getTimeZone("Asia/Tokyo");
    private static final TimeZone AMLA = TimeZone.getTimeZone("America/Los_Angeles");

    private static final Locale JPTYO = Locale.forLanguageTag("en-u-tz-jptyo");
    private static final Locale USLAX = Locale.forLanguageTag("en-u-tz-uslax");

    private static final Locale FW_SUN = Locale.forLanguageTag("en-US-u-fw-sun");
    private static final Locale FW_MON = Locale.forLanguageTag("en-US-u-fw-mon");
    private static final Locale FW_TUE = Locale.forLanguageTag("en-US-u-fw-tue");
    private static final Locale FW_WED = Locale.forLanguageTag("en-US-u-fw-wed");
    private static final Locale FW_THU = Locale.forLanguageTag("en-US-u-fw-thu");
    private static final Locale FW_FRI = Locale.forLanguageTag("en-US-u-fw-fri");
    private static final Locale FW_SAT = Locale.forLanguageTag("en-US-u-fw-sat");

    @BeforeTest
    public void beforeTest() {
        defaultTZ = TimeZone.getDefault();
        TimeZone.setDefault(AMLA);
    }

    @AfterTest
    public void afterTest() {
        TimeZone.setDefault(defaultTZ);
    }

    @DataProvider(name="tz")
    Object[][] tz() {
        return new Object[][] {
            // Locale, Expected Zone,
            {JPTYO, ASIATOKYO},
            {USLAX, AMLA},

            // invalid
            {Locale.forLanguageTag("en-US-u-tz-jpzzz"), AMLA}
        };
    }

    @DataProvider(name="firstDayOfWeek")
    Object[][] firstDayOfWeek () {
        return new Object[][] {
            // Locale, Expected DayOfWeek,
            {Locale.US, Calendar.SUNDAY},
            {FW_SUN, Calendar.SUNDAY},
            {FW_MON, Calendar.MONDAY},
            {FW_TUE, Calendar.TUESDAY},
            {FW_WED, Calendar.WEDNESDAY},
            {FW_THU, Calendar.THURSDAY},
            {FW_FRI, Calendar.FRIDAY},
            {FW_SAT, Calendar.SATURDAY},

            // invalid case
            {Locale.forLanguageTag("en-US-u-fw-xxx"), Calendar.SUNDAY},

            // region override
            {Locale.forLanguageTag("en-US-u-rg-gbzzzz"), Calendar.MONDAY},
            {Locale.forLanguageTag("zh-CN-u-rg-eszzzz"), Calendar.MONDAY},

            // "fw" and "rg".
            {Locale.forLanguageTag("en-US-u-fw-wed-rg-gbzzzz"), Calendar.WEDNESDAY},
            {Locale.forLanguageTag("en-US-u-fw-xxx-rg-gbzzzz"), Calendar.MONDAY},
            {Locale.forLanguageTag("en-US-u-fw-xxx-rg-zzzz"), Calendar.SUNDAY},
        };
    }

    @DataProvider(name="minDaysInFirstWeek")
    Object[][] minDaysInFrstWeek () {
        return new Object[][] {
            // Locale, Expected minDay,
            {Locale.US, 1},

            // region override
            {Locale.forLanguageTag("en-US-u-rg-gbzzzz"), 4},
            {Locale.forLanguageTag("zh-CN-u-rg-eszzzz"), 4},
        };
    }

    @Test(dataProvider="tz")
    public void test_tz(Locale locale, TimeZone zoneExpected) {
        DateFormat df = DateFormat.getTimeInstance(DateFormat.FULL, locale);
        assertEquals(df.getTimeZone(), zoneExpected);

        Calendar c = Calendar.getInstance(locale);
        assertEquals(c.getTimeZone(), zoneExpected);

        c = new Calendar.Builder().setLocale(locale).build();
        assertEquals(c.getTimeZone(), zoneExpected);
    }

    @Test(dataProvider="firstDayOfWeek")
    public void test_firstDayOfWeek(Locale locale, int dowExpected) {
        Calendar c = Calendar.getInstance(locale);
        assertEquals(c.getFirstDayOfWeek(), dowExpected);

        c = new Calendar.Builder().setLocale(locale).build();
        assertEquals(c.getFirstDayOfWeek(), dowExpected);
    }

    @Test(dataProvider="minDaysInFirstWeek")
    public void test_minDaysInFirstWeek(Locale locale, int minDaysExpected) {
        Calendar c = Calendar.getInstance(locale);
        assertEquals(c.getMinimalDaysInFirstWeek(), minDaysExpected);

        c = new Calendar.Builder().setLocale(locale).build();
        assertEquals(c.getMinimalDaysInFirstWeek(), minDaysExpected);
    }
}
