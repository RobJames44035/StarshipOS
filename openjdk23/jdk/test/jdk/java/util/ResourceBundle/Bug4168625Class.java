/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.util.ResourceBundle;
import java.util.MissingResourceException;
import java.util.Locale;

/**
 * Class loaded by custom class loader to load resources.
 * This call is called through the ResourceGetter interface
 * by the test.  The ResourceGetter interface is loaded
 * by the system loader to avoid ClassCastsExceptions.
 */
public class Bug4168625Class implements Bug4168625Getter {
        /** return the specified resource or null if not found */
    public ResourceBundle getResourceBundle(String resource, Locale locale) {
        try {
            return ResourceBundle.getBundle(resource, locale);
        } catch (MissingResourceException e) {
            return null;
        }
    }
}
