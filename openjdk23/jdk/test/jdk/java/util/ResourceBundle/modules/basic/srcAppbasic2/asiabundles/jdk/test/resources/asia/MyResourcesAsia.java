/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.resources.asia;

import java.util.Locale;
import jdk.test.resources.spi.MyResourcesProvider;

public class MyResourcesAsia extends MyResourcesProvider {
    public MyResourcesAsia() {
        super("java.properties");
    }

    @Override
    protected String toBundleName(String baseName, Locale locale) {
        // Convert baseName to its properties resource name for the given locale
        // e.g., jdk.test.resources.MyResources -> jdk/test/resources/asia/MyResources_zh_TW
        StringBuilder sb = new StringBuilder();
        int index = baseName.lastIndexOf('.');
        sb.append(baseName.substring(0, index))
            .append(".asia")
            .append(baseName.substring(index));
        String lang = locale.getLanguage();
        if (!lang.isEmpty()) {
            sb.append('_').append(lang);
            String country = locale.getCountry();
            if (!country.isEmpty()) {
                sb.append('_').append(country);
            }
        }
        return sb.toString();
    }

    @Override
    protected boolean isSupportedInModule(Locale locale) {
        return locale.equals(Locale.JAPANESE)
            || locale.equals(Locale.CHINESE) || locale.equals(Locale.TAIWAN);
    }
}
