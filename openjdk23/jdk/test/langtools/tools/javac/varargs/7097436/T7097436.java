/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.util.List;

class T7097436 {
    @SafeVarargs
    static void m(List<String>... ls) {
        Object o = ls; //warning
        Object[] oArr = ls; //warning
        String s = ls; // no warning
        Integer[] iArr = ls; // no warning
    }
}
