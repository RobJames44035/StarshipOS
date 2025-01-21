/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4486049
 * @summary min and max methods fail if size changes in between a call to size
 *           and an attempt to iterate.
 * @author Josh Bloch
 */

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class MinMax {
    public static void main(String[] args) {
        Set s = new LyingSet();
        s.add("x");
        if (!Collections.min(s).equals("x"))
            throw new RuntimeException("1: " + Collections.min(s));
        if (!Collections.max(s).equals("x"))
            throw new RuntimeException("2: " + Collections.max(s));

        s.add("y");
        if (!Collections.min(s).equals("x"))
            throw new RuntimeException("3: " + Collections.min(s));
        if (!Collections.max(s).equals("y"))
            throw new RuntimeException("4: " + Collections.max(s));

        s.add("w");
        if (!Collections.min(s).equals("w"))
            throw new RuntimeException("5: " + Collections.min(s));
        if (!Collections.max(s).equals("y"))
            throw new RuntimeException("6: " + Collections.max(s));

        s.clear();
        s.add("x");
        if (!Collections.min(s, Collections.reverseOrder()).equals("x"))
            throw new RuntimeException("1a: " + Collections.min(s));
        if (!Collections.max(s, Collections.reverseOrder()).equals("x"))
            throw new RuntimeException("2a: " + Collections.max(s));

        s.add("y");
        if (!Collections.min(s, Collections.reverseOrder()).equals("y"))
            throw new RuntimeException("3a: " + Collections.min(s));
        if (!Collections.max(s, Collections.reverseOrder()).equals("x"))
            throw new RuntimeException("4a: " + Collections.max(s));

        s.add("w");
        if (!Collections.min(s, Collections.reverseOrder()).equals("y"))
            throw new RuntimeException("5a: " + Collections.min(s));
        if (!Collections.max(s, Collections.reverseOrder()).equals("w"))
            throw new RuntimeException("6a: " + Collections.max(s));
    }
}

class LyingSet extends LinkedHashSet {
    public int size() {
        return super.size() + 1; // Lies, lies, all lies!
    }
}
