/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.resources.asia;

import java.util.Locale;
import jdk.test.resources.spi.MyResourcesProvider;

/**
 *
 */
public class MyResourcesAsia extends MyResourcesProvider {
    public MyResourcesAsia() {
        super("java.properties", "asia",
              Locale.JAPANESE, Locale.JAPAN, Locale.CHINESE, Locale.TAIWAN,
              Locale.of("vi"), Locale.of("in"));
    }
}
