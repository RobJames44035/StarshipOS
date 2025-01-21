/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.util.*;

class Conditional {
    void test() {
        String[] sa = null;
        List<String> ls = sa == null ? Arrays.asList(sa) :
            Collections.emptyList();
    }
}
