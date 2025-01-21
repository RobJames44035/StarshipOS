/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.resources.spi;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.spi.ResourceBundleProvider;

public class MyResourcesProvider implements ResourceBundleProvider {
    @Override
    public ResourceBundle getBundle(String baseName, Locale locale) {
        String xmlName = toXMLName(baseName, locale);
        try (InputStream is = this.getClass().getModule().getResourceAsStream(xmlName)) {
            return new XMLResourceBundle(new BufferedInputStream(is));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String toXMLName(String baseName, Locale locale) {
        StringBuilder sb = new StringBuilder(baseName.replace('.', '/'));
        String lang = locale.getLanguage();
        if (!lang.isEmpty()) {
            sb.append('_').append(lang);
            String country = locale.getCountry();
            if (!country.isEmpty()) {
                sb.append('_').append(country);
            }
        }
        return sb.append(".xml").toString();
    }

    private static class XMLResourceBundle extends ResourceBundle {
        private Properties props;

        XMLResourceBundle(InputStream stream) throws IOException {
            props = new Properties();
            props.loadFromXML(stream);
        }

        @Override
        protected Object handleGetObject(String key) {
            if (key == null) {
                throw new NullPointerException();
            }
            return props.get(key);
        }

        @Override
        public Enumeration<String> getKeys() {
            // Not implemented
            return null;
        }
    }
}
