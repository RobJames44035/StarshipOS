/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.resources.classes;

import java.util.ListResourceBundle;

public class MyResources extends ListResourceBundle {
    @Override
    public Object[][] getContents() {
        return new Object[][] {
            { "key", "root: message" }
        };
    }
}
