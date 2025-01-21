/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.resources.spi;


import java.util.Locale;
import java.util.ResourceBundle;
import java.util.spi.AbstractResourceBundleProvider;

public abstract class MyResourcesProvider extends AbstractResourceBundleProvider {
    protected MyResourcesProvider(String... formats) {
        super(formats);
    }

    @Override
    public ResourceBundle getBundle(String baseName, Locale locale) {
        if (isSupportedInModule(locale)) {
            return super.getBundle(baseName, locale);
        }
        return null;
    }

    protected abstract boolean isSupportedInModule(Locale locale);
}
