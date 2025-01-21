/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */
/*
 * @test
 * @bug 8177552 8221432
 * @summary Checks the behaviour of Unicode BCP 47 U Extension with
 *          compact number format
 * @modules jdk.localedata
 * @run testng/othervm TestUExtensionOverride
 */
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestUExtensionOverride {

    @DataProvider(name = "compactFormatData")
    Object[][] compactFormatData() {
        return new Object[][]{
            // locale, number, formatted string

            // -nu
            {Locale.forLanguageTag("en-US-u-nu-deva"), 12345, "\u0967\u0968K"},
            {Locale.forLanguageTag("en-US-u-nu-sinh"), 12345, "\u0de7\u0de8K"},
            {Locale.forLanguageTag("en-US-u-nu-zzzz"), 12345, "12K"},
            // -rg
            {Locale.forLanguageTag("fr-FR-u-rg-cazzzz"), 1234567,
                "1\u00a0M"},
            {Locale.forLanguageTag("fr-FR-u-rg-cazzzz"), 1234567890,
                "1\u00a0G"},
            // -nu and -rg
            {Locale.forLanguageTag("en-US-u-nu-deva-rg-dezzzz"), 12345,
                "\u0967\u0968K"},
            {Locale.forLanguageTag("fr-FR-u-nu-zzzz-rg-cazzzz"), 1234567890,
                "1\u00a0Md"},
            {Locale.forLanguageTag("fr-FR-u-nu-zzzz-rg-zzzz"), 12345,
                "12\u00a0k"},
            {Locale.forLanguageTag("fr-FR-u-rg-cazzzz-nu-deva"), 12345,
                "\u0967\u0968\u00a0k"},};
    }

    @DataProvider(name = "compactParseData")
    Object[][] compactParseData() {
        return new Object[][]{
            // locale, parse string, parsed number

            // -nu
            {Locale.forLanguageTag("en-US-u-nu-deva"),
                "\u0967\u0968K", 12000L},
            {Locale.forLanguageTag("en-US-u-nu-sinh"),
                "\u0de7\u0de8K", 12000L},
            {Locale.forLanguageTag("en-US-u-nu-zzzz"),
                "12K", 12000L},
            // -rg
            {Locale.forLanguageTag("fr-FR-u-rg-cazzzz"),
                "1\u00a0G", 1000000000L},
            // -nu and -rg
            {Locale.forLanguageTag("en-US-u-nu-deva-rg-dezzzz"),
                "\u0967\u0968K", 12000L},
            {Locale.forLanguageTag("fr-FR-u-nu-zzzz-rg-cazzzz"),
                "1\u00a0Md", 1000000000L},
            {Locale.forLanguageTag("fr-FR-u-nu-zzzz-rg-zzzz"),
                "12\u00a0k", 12000L},
            {Locale.forLanguageTag("fr-FR-u-rg-cazzzz-nu-deva"),
                "\u0967\u0968\u00a0k", 12000L},};
    }

    @Test(dataProvider = "compactFormatData")
    public void testFormat(Locale locale, double num,
            String expected) {
        NumberFormat cnf = NumberFormat.getCompactNumberInstance(locale,
                NumberFormat.Style.SHORT);
        CompactFormatAndParseHelper.testFormat(cnf, num, expected);
    }

    @Test(dataProvider = "compactParseData")
    public void testParse(Locale locale, String parseString,
            Number expected) throws ParseException {
        NumberFormat cnf = NumberFormat.getCompactNumberInstance(locale,
                NumberFormat.Style.SHORT);
        CompactFormatAndParseHelper.testParse(cnf, parseString, expected, null, null);
    }

}
