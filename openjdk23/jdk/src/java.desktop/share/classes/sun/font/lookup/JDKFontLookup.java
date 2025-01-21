/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package sun.font.lookup;

import sun.font.SunFontManager;

/**
 * Implementation-class accessed by other JDK modules to
 * locate the JDK-provided fonts.
 */
public final class JDKFontLookup {

    public static final String getJDKFontDir() {
        return SunFontManager.getJDKFontDir();
    }
}
