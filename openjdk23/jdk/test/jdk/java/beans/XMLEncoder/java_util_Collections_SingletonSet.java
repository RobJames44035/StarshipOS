/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 6505888
 * @summary Tests SingletonSet encoding
 * @run main/othervm java_util_Collections_SingletonSet
 * @author Sergey Malenkov
 */

import java.util.Collections;
import java.util.Set;

public final class java_util_Collections_SingletonSet extends AbstractTest<Set<String>> {
    public static void main(String[] args) {
        new java_util_Collections_SingletonSet().test();
    }

    protected Set<String> getObject() {
        return Collections.singleton("string");
    }

    protected Set<String> getAnotherObject() {
        return Collections.singleton("object");
    }
}
