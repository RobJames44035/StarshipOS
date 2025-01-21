/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 6505888
 * @summary Tests UnmodifiableRandomAccessList encoding
 * @run main/othervm java_util_Collections_UnmodifiableRandomAccessList
 * @author Sergey Malenkov
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class java_util_Collections_UnmodifiableRandomAccessList extends AbstractTest<List<String>> {
    public static void main(String[] args) {
        new java_util_Collections_UnmodifiableRandomAccessList().test();
    }

    protected List<String> getObject() {
        List<String> list = new ArrayList<String>();
        list.add("string");
        return Collections.unmodifiableList(list);
    }

    protected List<String> getAnotherObject() {
        List<String> list = new ArrayList<String>();
        return Collections.unmodifiableList(list);
    }
}
