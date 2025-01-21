/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 */

package sun.text.resources.ext;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.ResourceBundleBasedAdapter;

public class CollationData_zh_HK extends ListResourceBundle {

    // reparent to zh_TW for traditional Chinese collation sequence
    public CollationData_zh_HK() {
        ResourceBundle bundle = ((ResourceBundleBasedAdapter)LocaleProviderAdapter.forJRE()).getLocaleData().getCollationData(Locale.TAIWAN);
        setParent(bundle);
    }

    @Override
    protected final Object[][] getContents() {
        return new Object[][] {};
}
}
