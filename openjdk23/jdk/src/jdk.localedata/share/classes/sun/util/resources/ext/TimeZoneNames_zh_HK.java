/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package sun.util.resources.ext;

import java.util.Locale;
import java.util.ResourceBundle;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.ResourceBundleBasedAdapter;
import sun.util.resources.TimeZoneNamesBundle;

public final class TimeZoneNames_zh_HK extends TimeZoneNamesBundle {

    // reparent to zh_TW for traditional Chinese names
    public TimeZoneNames_zh_HK() {
        ResourceBundle bundle = ((ResourceBundleBasedAdapter)LocaleProviderAdapter.forJRE()).getLocaleData().getTimeZoneNames(Locale.TAIWAN);
        setParent(bundle);
    }

    @Override
    protected Object[][] getContents() {
        return new Object[][] {};
    }
}
