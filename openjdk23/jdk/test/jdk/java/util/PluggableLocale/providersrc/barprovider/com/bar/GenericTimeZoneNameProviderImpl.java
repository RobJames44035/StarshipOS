/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
/*
 *
 */

package com.bar;

import java.util.*;
import java.util.spi.*;

import com.foobar.Utils;

/**
 * Implementation class for getGenericTimeZoneName which returns "Generic "+<standard name in OSAKA>.
 */
public class GenericTimeZoneNameProviderImpl extends TimeZoneNameProviderImpl {
    static final Locale jaJPGeneric = Locale.of("ja", "JP", "generic");
    static final Locale OSAKA = Locale.of("ja", "JP", "osaka");

    static Locale[] avail = {
        jaJPGeneric
    };

    @Override
    public Locale[] getAvailableLocales() {
        return avail;
    }

    @Override
    public String getGenericDisplayName(String id, int style, Locale locale) {
        if (!jaJPGeneric.equals(locale)) {
            return null;
        }
        String std = super.getDisplayName(id, false, style, OSAKA);
        return (std != null) ? "Generic " + std : null;
    }
}
