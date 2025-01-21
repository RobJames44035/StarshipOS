/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.resources.asia;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.spi.AbstractResourceBundleProvider;

import jdk.test.resources.spi.MyResourcesProvider;

public class MyResourcesAsia extends AbstractResourceBundleProvider
    implements MyResourcesProvider
{
    private static Set<Locale> asiaLocales
        = Set.of(Locale.JAPANESE, Locale.CHINESE, Locale.TAIWAN);

    @Override
    public String toBundleName(String baseName, Locale locale) {
        String bundleName = super.toBundleName(baseName, locale);
        if (asiaLocales.contains(locale)) {
            int index = bundleName.lastIndexOf('.');
            return bundleName.substring(0, index + 1) + "asia" + bundleName.substring(index);
        }
        return bundleName;
    }

    @Override
    public ResourceBundle getBundle(String baseName, Locale locale) {
        if (asiaLocales.contains(locale)) {
            return super.getBundle(baseName, locale);
        }
        return null;
    }
}
