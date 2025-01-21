/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package sun.font;

import sun.awt.Win32FontManager;

final class PlatformFontInfo {

    /**
     * The method is only to be called via the
     * {@code FontManagerFactory.getInstance()} factory method.
     */
    static FontManager createFontManager() {
        return new Win32FontManager();
    }
}
