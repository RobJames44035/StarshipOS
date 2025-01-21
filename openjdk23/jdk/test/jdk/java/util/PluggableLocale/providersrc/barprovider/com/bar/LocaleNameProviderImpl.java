/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
/*
 *
 */

package com.bar;

import java.text.*;
import java.util.*;
import java.util.spi.*;

import com.foobar.Utils;

public class LocaleNameProviderImpl extends LocaleNameProvider {
    static Locale[] avail = {Locale.JAPANESE,
                             Locale.JAPAN,
                             Locale.of("ja", "JP", "osaka"),
                             Locale.of("ja", "JP", "kyoto"),
                             Locale.of("xx"),
                             Locale.of("yy", "YY", "YYYY")};
    static List<Locale> availList = Arrays.asList(avail);
    public Locale[] getAvailableLocales() {
        return avail;
    }

    @Override
    public String getDisplayLanguage(String lang, Locale target) {
        return getDisplayString(lang, target);
    }

    @Override
    public String getDisplayCountry(String ctry, Locale target) {
        return getDisplayString(ctry, target);
    }

    @Override
    public String getDisplayVariant(String vrnt, Locale target) {
        return getDisplayString(vrnt, target);
    }

    private String getDisplayString(String key, Locale target) {
        if (!Utils.supportsLocale(availList, target)) {
            throw new IllegalArgumentException("locale is not supported: "+target);
        }

        String ret = null;

        if (target.getLanguage().equals("yy") &&
            target.getCountry().equals("YY")) {
            String vrnt = target.getVariant();
            if (vrnt.startsWith("YYYY")) {
                switch (key) {
                    case "yy":
                    case "YY":
                        ret = "waiwai";
                        break;

                    case "YYYY":
                        if (vrnt.equals("YYYY_suffix")) {
                            // for LocaleNameProviderTest.variantFallbackTest()
                            throw new RuntimeException(vrnt);
                        } else {
                            ret = "waiwai";
                        }
                        break;
                }
            }
        } else {
            // resource bundle based (allows fallback)
        try {
            ResourceBundle rb = ResourceBundle.getBundle("com.bar.LocaleNames", target);
                ret = rb.getString(key);
        } catch (MissingResourceException mre) {
        }
        }

        return ret;
    }
        }
