/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.resources.props.spi;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.spi.AbstractResourceBundleProvider;

public class MyResourcesProvider extends AbstractResourceBundleProvider {
    public MyResourcesProvider() {
        super("java.properties");
    }

    @Override
    protected String toBundleName(String baseName, Locale locale) {
        StringBuilder sb = new StringBuilder(baseName);
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
    public ResourceBundle getBundle(String baseName, Locale locale) {
        ResourceBundle rb = super.getBundle(baseName, locale);
        String tag = locale.toLanguageTag();
        if (tag.equals("und")) {
            tag = "ROOT"; // to a human friendly name
        }
        System.out.printf("    MyResourcesProvider.getBundle(%s, %s)%n         -> %s%n",
                          baseName, tag, rb);
        return rb;
    }
}
