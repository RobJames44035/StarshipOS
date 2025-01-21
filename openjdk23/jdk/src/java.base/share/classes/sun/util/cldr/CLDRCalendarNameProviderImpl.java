/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package sun.util.cldr;

import static sun.util.locale.provider.LocaleProviderAdapter.Type;

import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.AvailableLanguageTags;
import sun.util.locale.provider.CalendarNameProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;


public class CLDRCalendarNameProviderImpl extends CalendarNameProviderImpl implements AvailableLanguageTags{

    public CLDRCalendarNameProviderImpl(Type type, Set<String> langtags) {
        super(type, langtags);
    }

    @Override
    public boolean isSupportedLocale(Locale locale) {
        if (Locale.ROOT.equals(locale)) {
            return true;
        }
        String calendarType = null;
        if (locale.hasExtensions()) {
            calendarType = locale.getUnicodeLocaleType("ca");
            locale = locale.stripExtensions();
        }
        if (calendarType != null) {
            switch (calendarType) {
                case "buddhist":
                case "japanese":
                case "gregory":
                case "islamic":
                case "roc":
                    break;
                default:
                    // Unknown calendar type
                    return false;
            }
        }
        return LocaleProviderAdapter.forType(Type.CLDR).isSupportedProviderLocale(locale, langtags);
    }
}
