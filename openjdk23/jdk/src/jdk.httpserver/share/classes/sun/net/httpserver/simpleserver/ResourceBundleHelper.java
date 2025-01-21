/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package sun.net.httpserver.simpleserver;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

class ResourceBundleHelper {
    static final ResourceBundle bundle;

    static {
        try {
            bundle = ResourceBundle.getBundle("sun.net.httpserver.simpleserver.resources.simpleserver");
        } catch (MissingResourceException e) {
            throw new InternalError("Cannot find simpleserver resource bundle for locale " + Locale.getDefault());
        }
    }

    static String getMessage(String key, Object... args) {
        try {
            return MessageFormat.format(bundle.getString(key), args);
        } catch (MissingResourceException e) {
            throw new InternalError("Missing message: " + key);
        }
    }
}
