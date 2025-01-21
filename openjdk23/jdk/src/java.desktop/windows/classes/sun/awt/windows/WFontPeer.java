/*
 * StarshipOS Copyright (c) 1996-2025. R.A. James
 */

package sun.awt.windows;

import sun.awt.PlatformFont;

final class WFontPeer extends PlatformFont {

    private String textComponentFontName;

    public WFontPeer(String name, int style){
        super(name, style);
        if (fontConfig != null) {
            textComponentFontName = ((WFontConfiguration) fontConfig).getTextComponentFontName(familyName, style);
        }
    }

    @Override
    protected char getMissingGlyphCharacter() {
        return '\u2751';
    }

    static {
        /* NB Headless printing uses AWT Fonts */
        initIDs();
    }

    /**
     * Initialize JNI field and method IDs
     */
    private static native void initIDs();
}
