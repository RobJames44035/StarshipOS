/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6752638
 * @summary Test no NPE calling preferLocaleFonts() on custom GE.
 * @run main PreferLocaleFonts
 */

import java.util.*;
import java.awt.*;
import java.awt.image.*;

public class PreferLocaleFonts extends GraphicsEnvironment {

    public static void main(String args[]) {
(new PreferLocaleFonts()).preferLocaleFonts();
    }
    public PreferLocaleFonts() {
        super();
    }
    public Graphics2D createGraphics(BufferedImage image) {
        return null;
    }
    public String[] getAvailableFontFamilyNames(Locale locale) {
        return null;
    }
    public String[] getAvailableFontFamilyNames() {
        return null;
    }
    public Font[] getAllFonts() {
        return null;
    }
    public GraphicsDevice getDefaultScreenDevice() throws HeadlessException {
        return null;
    }
    public GraphicsDevice[] getScreenDevices() throws HeadlessException {
        return null;
    }
}

