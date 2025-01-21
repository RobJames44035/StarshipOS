/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file:
 *
 * Written by Doug Lea and Martin Buchholz with assistance from
 * members of JCP JSR-166 Expert Group and released to the public
 * domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.Test;

public class LinkedHashMapTest extends JSR166TestCase {
    public static void main(String[] args) {
        main(suite(), args);
    }

    public static Test suite() {
        class Implementation implements MapImplementation {
            public Class<?> klazz() { return LinkedHashMap.class; }
            public Map emptyMap() { return new LinkedHashMap(); }
            public boolean isConcurrent() { return false; }
            public boolean permitsNullKeys() { return true; }
            public boolean permitsNullValues() { return true; }
            public boolean supportsSetValue() { return true; }
        }
        return newTestSuite(
            // LinkedHashMapTest.class,
            MapTest.testSuite(new Implementation()));
    }
}
