/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */


package java2d;


import java.awt.Font;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A cache of the dynamically loaded fonts found in the fonts directory.
 */
public class DemoFonts {

    // Prepare a static "cache" mapping font names to Font objects.
    private static final String[] names =  { "A.ttf" };
    private static final Map<String,Font> cache =
               new ConcurrentHashMap<String,Font>(names.length);
    static {
        for (String name : names) {
            cache.put(name, getFont(name));
        }
    }

    public static void newDemoFonts() {
    }


    public static Font getFont(String name) {
        Font font = null;
        if (cache != null) {
            if ((font = cache.get(name)) != null) {
                return font;
            }
        }
        String fName = "/fonts/" + name;
        try {
            InputStream is = DemoFonts.class.getResourceAsStream(fName);
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception ex) {
            Logger.getLogger(DemoFonts.class.getName()).log(Level.SEVERE,
                    fName + " not loaded.  Using serif font.", ex);
            font = new Font(Font.SERIF, Font.PLAIN, 24);
        }
        return font;
    }
}
