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

public class CurrencyNameProviderImpl2 extends CurrencyNameProvider {
    static Locale[] avail = {Locale.of("ja", "JP", "tokyo"),
                             Locale.of("ja", "JP", "osaka"), };
    public Locale[] getAvailableLocales() {
        return avail;
    }

    @Override
    public String getSymbol(String c, Locale locale) {
        if (!Utils.supportsLocale(Arrays.asList(avail), locale)) {
            throw new IllegalArgumentException("locale is not supported: "+locale);
        }

        if (c.equals("JPY")) {
            if (Utils.supportsLocale(avail[0], locale)) {
                return "JPY-tokyo";
            } else if (Utils.supportsLocale(avail[1], locale)) {
                return "JPY-osaka";
            }
        }
        return null;
    }

    @Override
    public String getDisplayName(String c, Locale locale) {
        if (!Utils.supportsLocale(Arrays.asList(avail), locale)) {
            throw new IllegalArgumentException("locale is not supported: "+locale);
        }

        if (c.equals("JPY")) {
            if (Utils.supportsLocale(avail[0], locale)) {
                return "JPY-tokyo";
            } else if (Utils.supportsLocale(avail[1], locale)) {
                return "JPY-osaka";
            }
        }
        return null;
    }
}
