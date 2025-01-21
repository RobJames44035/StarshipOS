/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 6505888
 * @summary Tests UnmodifiableSet encoding
 * @run main/othervm java_util_Collections_UnmodifiableSet
 * @author Sergey Malenkov
 */

import java.util.Collections;
import java.util.Set;

public final class java_util_Collections_UnmodifiableSet extends AbstractTest<Set<String>> {
    public static void main(String[] args) {
        new java_util_Collections_UnmodifiableSet().test();
    }

    protected Set<String> getObject() {
        Set<String> set = Collections.singleton("string");
        return Collections.unmodifiableSet(set);
    }

    protected Set<String> getAnotherObject() {
        Set<String> set = Collections.emptySet();
        return Collections.unmodifiableSet(set);
    }
}
