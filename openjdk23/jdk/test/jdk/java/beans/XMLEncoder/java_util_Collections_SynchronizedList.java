/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 6505888
 * @summary Tests SynchronizedList encoding
 * @run main/othervm java_util_Collections_SynchronizedList
 * @author Sergey Malenkov
 */

import java.util.Collections;
import java.util.List;

public final class java_util_Collections_SynchronizedList extends AbstractTest<List<String>> {
    public static void main(String[] args) {
        new java_util_Collections_SynchronizedList().test();
    }

    protected List<String> getObject() {
        List<String> list = Collections.singletonList("string");
        return Collections.synchronizedList(list);
    }

    protected List<String> getAnotherObject() {
        List<String> list = Collections.emptyList();
        return Collections.synchronizedList(list);
    }
}
