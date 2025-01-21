/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package sun.util.locale.provider;

import java.util.Locale;

/**
 * Singletons for the well-known JRE-specific Locales. (th_TH isn't JRE specific,
 * but it's treated as a special Locale because of the Thai Buddhist calendar
 * support.)
 *
 * @author Masayoshi Okutsu
 */
public class JRELocaleConstants {
    public static final Locale JA_JP_JP = Locale.of("ja", "JP", "JP");
    public static final Locale NO_NO_NY = Locale.of("no", "NO", "NY");
    public static final Locale TH_TH    = Locale.of("th", "TH");
    public static final Locale TH_TH_TH = Locale.of("th", "TH", "TH");

    private JRELocaleConstants() {
    }
}
