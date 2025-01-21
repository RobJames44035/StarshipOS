/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package sun.util.resources.ext;

import sun.util.resources.TimeZoneNamesBundle;

public final class TimeZoneNames extends TimeZoneNamesBundle {
    /**
     * Exists to keep sun.util.resources.ext package alive
     * with IncludeLocales jlink plugin
     */
    @Override
    protected Object[][] getContents() {
        return new Object[][]{};
    }
}
