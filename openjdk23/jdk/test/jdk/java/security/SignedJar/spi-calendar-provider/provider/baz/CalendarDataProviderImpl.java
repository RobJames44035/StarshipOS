/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package baz;

import static java.util.Calendar.*;
import java.util.Locale;
import java.util.spi.CalendarDataProvider;

public class CalendarDataProviderImpl extends CalendarDataProvider {
    private static final Locale[] locales = { new Locale("xx", "YY") };

    @Override
    public int getFirstDayOfWeek(Locale locale) {
        return WEDNESDAY;
    }

    @Override
    public int getMinimalDaysInFirstWeek(Locale locale) {
        if (locale.getLanguage().equals("xx")) {
            System.out.println("DEBUG: Getting xx language");
        }
        return 7;
    }

    @Override
    public Locale[] getAvailableLocales() {
        return locales;
    }
}
