/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @modules jdk.localedata
 * @bug 8231273
 */

package test.java.time.format;

import static java.time.temporal.ChronoField.AMPM_OF_DAY;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.testng.Assert.assertEquals;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalField;
import java.util.Locale;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test SimpleDateTimeTextProviderWithLocale.
 */
@Test
public class TestDateTimeTextProviderWithLocale extends AbstractTestPrinterParser {

    Locale enUS = Locale.US;
    Locale ptBR = Locale.of("pt", "BR");

    //-----------------------------------------------------------------------
    @DataProvider(name = "Text")
    Object[][] data_text() {
        return new Object[][] {
            {DAY_OF_WEEK, 1, TextStyle.SHORT, ptBR, "seg."},
            {DAY_OF_WEEK, 2, TextStyle.SHORT, ptBR, "ter."},
            {DAY_OF_WEEK, 3, TextStyle.SHORT, ptBR, "qua."},
            {DAY_OF_WEEK, 4, TextStyle.SHORT, ptBR, "qui."},
            {DAY_OF_WEEK, 5, TextStyle.SHORT, ptBR, "sex."},
            {DAY_OF_WEEK, 6, TextStyle.SHORT, ptBR, "s\u00E1b."},
            {DAY_OF_WEEK, 7, TextStyle.SHORT, ptBR, "dom."},

            {DAY_OF_WEEK, 1, TextStyle.FULL, ptBR, "segunda-feira"},
            {DAY_OF_WEEK, 2, TextStyle.FULL, ptBR, "ter\u00E7a-feira"},
            {DAY_OF_WEEK, 3, TextStyle.FULL, ptBR, "quarta-feira"},
            {DAY_OF_WEEK, 4, TextStyle.FULL, ptBR, "quinta-feira"},
            {DAY_OF_WEEK, 5, TextStyle.FULL, ptBR, "sexta-feira"},
            {DAY_OF_WEEK, 6, TextStyle.FULL, ptBR, "s\u00E1bado"},
            {DAY_OF_WEEK, 7, TextStyle.FULL, ptBR, "domingo"},

            {MONTH_OF_YEAR, 1, TextStyle.SHORT, ptBR, "jan."},
            {MONTH_OF_YEAR, 2, TextStyle.SHORT, ptBR, "fev."},
            {MONTH_OF_YEAR, 3, TextStyle.SHORT, ptBR, "mar."},
            {MONTH_OF_YEAR, 4, TextStyle.SHORT, ptBR, "abr."},
            {MONTH_OF_YEAR, 5, TextStyle.SHORT, ptBR, "mai."},
            {MONTH_OF_YEAR, 6, TextStyle.SHORT, ptBR, "jun."},
            {MONTH_OF_YEAR, 7, TextStyle.SHORT, ptBR, "jul."},
            {MONTH_OF_YEAR, 8, TextStyle.SHORT, ptBR, "ago."},
            {MONTH_OF_YEAR, 9, TextStyle.SHORT, ptBR, "set."},
            {MONTH_OF_YEAR, 10, TextStyle.SHORT, ptBR, "out."},
            {MONTH_OF_YEAR, 11, TextStyle.SHORT, ptBR, "nov."},
            {MONTH_OF_YEAR, 12, TextStyle.SHORT, ptBR, "dez."},

            {MONTH_OF_YEAR, 1, TextStyle.FULL, ptBR, "janeiro"},
            {MONTH_OF_YEAR, 2, TextStyle.FULL, ptBR, "fevereiro"},
            {MONTH_OF_YEAR, 3, TextStyle.FULL, ptBR, "mar\u00E7o"},
            {MONTH_OF_YEAR, 4, TextStyle.FULL, ptBR, "abril"},
            {MONTH_OF_YEAR, 5, TextStyle.FULL, ptBR, "maio"},
            {MONTH_OF_YEAR, 6, TextStyle.FULL, ptBR, "junho"},
            {MONTH_OF_YEAR, 7, TextStyle.FULL, ptBR, "julho"},
            {MONTH_OF_YEAR, 8, TextStyle.FULL, ptBR, "agosto"},
            {MONTH_OF_YEAR, 9, TextStyle.FULL, ptBR, "setembro"},
            {MONTH_OF_YEAR, 10, TextStyle.FULL, ptBR, "outubro"},
            {MONTH_OF_YEAR, 11, TextStyle.FULL, ptBR, "novembro"},
            {MONTH_OF_YEAR, 12, TextStyle.FULL, ptBR, "dezembro"},

        };
    }

    @Test(dataProvider = "Text")
    public void test_getText(TemporalField field, Number value, TextStyle style, Locale locale, String expected) {
          DateTimeFormatter fmt = getFormatter(field, style).withLocale(locale);
          assertEquals(fmt.format(ZonedDateTime.now().with(field, value.longValue())), expected);
    }

}
