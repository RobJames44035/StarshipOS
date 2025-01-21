/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 6505888
 * @summary Tests SynchronizedSet encoding
 * @run main/othervm java_util_Collections_SynchronizedSet
 * @author Sergey Malenkov
 */

import java.util.Collections;
import java.util.Set;

public final class java_util_Collections_SynchronizedSet extends AbstractTest<Set<String>> {
    public static void main(String[] args) {
        new java_util_Collections_SynchronizedSet().test();
    }

    protected Set<String> getObject() {
        Set<String> set = Collections.singleton("string");
        return Collections.synchronizedSet(set);
    }

    protected Set<String> getAnotherObject() {
        Set<String> set = Collections.emptySet();
        return Collections.synchronizedSet(set);
    }
}
