/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 6505888
 * @summary Tests SynchronizedSortedSet encoding
 * @run main/othervm java_util_Collections_SynchronizedSortedSet
 * @author Sergey Malenkov
 */

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public final class java_util_Collections_SynchronizedSortedSet extends AbstractTest<SortedSet<String>> {
    public static void main(String[] args) {
        new java_util_Collections_SynchronizedSortedSet().test();
    }

    protected SortedSet<String> getObject() {
        SortedSet<String> set = new TreeSet<String>();
        set.add("string");
        return Collections.synchronizedSortedSet(set);
    }

    protected SortedSet<String> getAnotherObject() {
        SortedSet<String> set = new TreeSet<String>();
        return Collections.synchronizedSortedSet(set);
    }
}
