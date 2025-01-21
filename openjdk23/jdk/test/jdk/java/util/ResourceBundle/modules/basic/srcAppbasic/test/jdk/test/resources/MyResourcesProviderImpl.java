/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.resources;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.spi.AbstractResourceBundleProvider;
import jdk.test.resources.spi.MyResourcesProvider;

public class MyResourcesProviderImpl extends AbstractResourceBundleProvider
    implements MyResourcesProvider
{
    public MyResourcesProviderImpl() {
        super("java.class");
    }
    @Override
    public ResourceBundle getBundle(String baseName, Locale locale) {
        if (locale.equals(Locale.ENGLISH) || locale.equals(Locale.ROOT)) {
            return super.getBundle(baseName, locale);
        }
        return null;
    }
}
