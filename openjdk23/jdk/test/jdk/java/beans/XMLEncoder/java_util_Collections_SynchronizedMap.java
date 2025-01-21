/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 6505888
 * @summary Tests SynchronizedMap encoding
 * @run main/othervm java_util_Collections_SynchronizedMap
 * @author Sergey Malenkov
 */

import java.util.Collections;
import java.util.Map;

public final class java_util_Collections_SynchronizedMap extends AbstractTest<Map<String, String>> {
    public static void main(String[] args) {
        new java_util_Collections_SynchronizedMap().test();
    }

    protected Map<String, String> getObject() {
        Map<String, String> map = Collections.singletonMap("key", "value");
        return Collections.synchronizedMap(map);
    }

    protected Map<String, String> getAnotherObject() {
        Map<String, String> map = Collections.emptyMap();
        return Collections.synchronizedMap(map);
    }
}
