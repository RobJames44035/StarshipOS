/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 6505888
 * @summary Tests SingletonList encoding
 * @run main/othervm java_util_Collections_SingletonList
 * @author Sergey Malenkov
 */

import java.util.Collections;
import java.util.List;

public final class java_util_Collections_SingletonList extends AbstractTest<List<String>> {
    public static void main(String[] args) {
        new java_util_Collections_SingletonList().test();
    }

    protected List<String> getObject() {
        return Collections.singletonList("string");
    }

    protected List<String> getAnotherObject() {
        return Collections.singletonList("object");
    }
}
