/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package p.internal;

import p.resources.spi.MyResourceProvider;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.spi.AbstractResourceBundleProvider;

public class BundleProvider extends AbstractResourceBundleProvider
    implements MyResourceProvider {
    public BundleProvider() {
        super();
    }
    @Override
    public ResourceBundle getBundle(String baseName, Locale locale) {
        if (locale.equals(Locale.ENGLISH) || locale.equals(Locale.ROOT)) {
            return super.getBundle(baseName, locale);
        }

        return null;
    }

}
