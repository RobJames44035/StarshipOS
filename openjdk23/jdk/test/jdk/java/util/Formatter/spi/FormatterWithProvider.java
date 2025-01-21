/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */
/*
 * @test
 * @bug 8199672 8174269
 * @summary test the Formatter.format() method with java.locale.providers=SPI,
 *          CLDR. It should not throw ClassCastException if an SPI is
 *          used and NumberFormat.getInstance() does not return a
 *          DecimalFormat object.
 * @modules jdk.localedata
 * @library provider
 * @build provider/module-info provider/test.NumberFormatProviderImpl
 * @run main/othervm -Djava.locale.providers=SPI,CLDR FormatterWithProvider
 */

import java.util.Formatter;
import java.util.Locale;

public class FormatterWithProvider {

    public static void main(String[] args) {

        Integer number = 1234567;
        String formatString = "%,d";

        try {
            testFormatter(Locale.JAPANESE, formatString, number);
            testFormatter(Locale.FRENCH, formatString, number);
            testFormatter(Locale.of("hi", "IN"), formatString, number);

        } catch (ClassCastException ex) {
            throw new RuntimeException("[FAILED: A ClassCastException is" +
                    " thrown while using Formatter.format() with VM" +
                    " argument java.locale.providers=SPI,CLDR]", ex);
        }
    }

    private static void testFormatter(Locale locale, String formatString,
                                      Integer number) {

        // test using String.format
        String.format(locale, formatString, number);
        // test using Formatter's format
        Formatter formatter = new Formatter(locale);
        formatter.format(formatString, number);
    }

}

