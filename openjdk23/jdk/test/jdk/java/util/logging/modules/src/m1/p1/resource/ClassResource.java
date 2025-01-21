/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package p1.resource;

import java.util.ListResourceBundle;

public class ClassResource extends ListResourceBundle {

    protected Object[][] getContents() {
        return new Object[][] {
                { "OkKey", "OK" },
                { "CancelKey", "Cancel" }, };
    }
}
