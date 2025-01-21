/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6777487
 * @summary Tests private field access for CheckedSortedMap
 * @run main TestCheckedSortedMap
 */

import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

public final class TestCheckedSortedMap {
    private static final Object OBJECT = new Object();

    public static void main(String[] args) {
        SortedMap<String, String> map = new TreeMap<String, String>();
        TestEncoder.test(
                Collections.checkedSortedMap(map, String.class, String.class),
                new TreeMap() {
                    private final Object keyType = OBJECT;
                    private final Object valueType = OBJECT;
                },
                OBJECT
        );
    }
}
