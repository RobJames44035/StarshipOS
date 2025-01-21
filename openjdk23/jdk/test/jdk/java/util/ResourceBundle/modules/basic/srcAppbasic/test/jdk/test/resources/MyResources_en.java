/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.resources;

import java.util.ListResourceBundle;

public class MyResources_en extends ListResourceBundle {
    @Override
    public Object[][] getContents() {
        return new Object[][] {
            { "key", "en: message" }
        };
    }
}
