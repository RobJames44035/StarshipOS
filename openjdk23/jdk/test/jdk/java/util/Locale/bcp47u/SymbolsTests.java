/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 *
 * @test
 * @bug 8176841 8194148
 * @summary Tests *FormatSymbols class deals with Unicode extensions
 *      correctly.
 * @modules jdk.localedata
 * @run testng/othervm -Djava.locale.providers=CLDR SymbolsTests
 */

import static org.testng.Assert.assertEquals;

import java.text.DateFormatSymbols;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test *FormatSymbols classes with BCP47 U extensions
 */
@Test
public class SymbolsTests {

    private static final Locale RG_GB = Locale.forLanguageTag("en-US-u-rg-gbzzzz");
    private static final Locale RG_IE = Locale.forLanguageTag("en-US-u-rg-iezzzz");
    private static final Locale RG_AT = Locale.forLanguageTag("en-US-u-rg-atzzzz");

    @DataProvider(name="dateFormatSymbolsData")
    Object[][] dateFormatSymbolsData() {
        return new Object[][] {
            // Locale, expected AM string, expected PM string

            {RG_GB, "am", "pm"},
            {RG_IE, "a.m.", "p.m."},
            {Locale.US, "AM", "PM"},
        };
    }

    @DataProvider(name="decimalFormatSymbolsData")
    Object[][] decimalFormatSymbolsData() {
        return new Object[][] {
            // Locale, expected decimal separator, expected grouping separator

            {RG_AT, ',', '.'},
            {Locale.US, '.', ','},

            // -nu & -rg mixed. -nu should win
            {Locale.forLanguageTag("ar-EG-u-nu-latn-rg-mazzzz"), '.', ','},
        };
    }

    @Test(dataProvider="dateFormatSymbolsData")
    public void test_DateFormatSymbols(Locale locale, String amExpected, String pmExpected) {
        DateFormatSymbols dfs = DateFormatSymbols.getInstance(locale);
        String[] ampm = dfs.getAmPmStrings();
        assertEquals(ampm[0], amExpected);
        assertEquals(ampm[1], pmExpected);
    }

    @Test(dataProvider="decimalFormatSymbolsData")
    public void test_DecimalFormatSymbols(Locale locale, char decimal, char grouping) {
        DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(locale);
        assertEquals(dfs.getDecimalSeparator(), decimal);
        assertEquals(dfs.getGroupingSeparator(), grouping);
    }
}
