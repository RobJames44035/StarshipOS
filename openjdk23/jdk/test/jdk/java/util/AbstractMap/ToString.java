/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4486049
 * @summary toString method fails if size changes in between a call to size
 *           and an attempt to iterate.
 * @author Josh Bloch
 */

import java.util.LinkedHashMap;
import java.util.Map;

public class ToString {
    public static void main(String[] args) {
        Map m = new LyingMap();
        if (!m.toString().equals("{}"))
            throw new RuntimeException(m.toString() + "!= {}");

        m.put("x", "1");
        if (!m.toString().equals("{x=1}"))
            throw new RuntimeException(m.toString() + "!= {x=1}");

        m.put("y", "2");
        if (!m.toString().equals("{x=1, y=2}"))
            throw new RuntimeException(m.toString() + "!= {x=1, y=2}");
    }
}

class LyingMap extends LinkedHashMap {
    public int size() {
        return super.size() + 1; // Lies, lies, all lies!
    }
}
