/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 7041232
 * @summary verify that an unexpected exception isn't thrown for unnatural data to keep backward compatibility with JDK 6.
 */
import java.text.*;

public class Bug7041232 {

    public static void main(String[] args) {
        String UnicodeChars;
        StringBuffer sb = new StringBuffer();

        // Generates String which includes U+2028(line separator) and
        // U+2029(paragraph separator)
        for (int i = 0x2000; i < 0x2100; i++) {
            sb.append((char)i);
        }
        UnicodeChars = sb.toString();

        Bidi bidi = new Bidi(UnicodeChars, Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
        bidi.createLineBidi(0, UnicodeChars.length());
    }

}
