/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 6505888
 * @summary Tests SynchronizedSortedMap encoding
 * @run main/othervm java_util_Collections_SynchronizedSortedMap
 * @author Sergey Malenkov
 */

import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

public final class java_util_Collections_SynchronizedSortedMap extends AbstractTest<SortedMap<String, String>> {
    public static void main(String[] args) {
        new java_util_Collections_SynchronizedSortedMap().test();
    }

    protected SortedMap<String, String> getObject() {
        SortedMap<String, String> map = new TreeMap<String, String>();
        map.put("key", "value");
        return Collections.synchronizedSortedMap(map);
    }

    protected SortedMap<String, String> getAnotherObject() {
        SortedMap<String, String> map = new TreeMap<String, String>();
        return Collections.synchronizedSortedMap(map);
    }
}
