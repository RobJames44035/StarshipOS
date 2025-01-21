/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.util.HashSet;
import java.util.Set;

class Warning
{
    static void useUnchecked() {
        Set s = new HashSet<String>();
        s.add("abc");
    }
}
