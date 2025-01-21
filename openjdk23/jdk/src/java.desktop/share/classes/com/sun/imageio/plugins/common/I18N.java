/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package com.sun.imageio.plugins.common;

public final class I18N extends I18NImpl {
    private static final String resource_name = "iio-plugin.properties";
    public static String getString(String key) {
        return getString("com.sun.imageio.plugins.common.I18N", resource_name, key);
    }
}
