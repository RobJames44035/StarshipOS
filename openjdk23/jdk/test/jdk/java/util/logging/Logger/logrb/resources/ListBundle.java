/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
package resources;

import java.util.ListResourceBundle;

/**
 *
 * @author danielfuchs
 */
public class ListBundle extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][] {
            { "dummy", "foo" },
        };

    }

}
