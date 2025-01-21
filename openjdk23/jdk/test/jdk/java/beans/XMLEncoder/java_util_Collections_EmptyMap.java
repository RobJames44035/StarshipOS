/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 6505888
 * @summary Tests EmptyMap encoding
 * @run main/othervm java_util_Collections_EmptyMap
 * @author Sergey Malenkov
 */

import java.util.Collections;
import java.util.Map;

public final class java_util_Collections_EmptyMap extends AbstractTest<Map<String, String>> {
    public static void main(String[] args) {
        new java_util_Collections_EmptyMap().test();
    }

    protected Map<String, String> getObject() {
        return Collections.emptyMap();
    }

    protected Map<String, String> getAnotherObject() {
        return Collections.singletonMap("key", "value");
    }
}
