/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */
package test;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.spi.NumberFormatProvider;
import java.util.Locale;

public class NumberFormatProviderImpl extends NumberFormatProvider {

    private static final Locale[] locales = {Locale.FRENCH, Locale.JAPANESE,
            Locale.of("hi", "IN"), Locale.of("xx", "YY")};

    @Override
    public NumberFormat getCurrencyInstance(Locale locale) {
        return null;
    }

    @Override
    public NumberFormat getIntegerInstance(Locale locale) {
        return null;
    }

    @Override
    public NumberFormat getNumberInstance(Locale locale) {
        if (locale.getLanguage().equals("xx")) {
            return new DecimalFormat("#0.###", DecimalFormatSymbols.getInstance(Locale.US));
        } else {
            return null;
        }
    }

    @Override
    public NumberFormat getPercentInstance(Locale locale) {
        return null;
    }

    @Override
    public Locale[] getAvailableLocales() {
        return locales;
    }
}
