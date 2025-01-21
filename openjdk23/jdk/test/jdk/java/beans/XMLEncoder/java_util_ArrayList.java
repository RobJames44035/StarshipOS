/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 4631471
 * @summary Tests ArrayList encoding
 * @run main/othervm java_util_ArrayList
 * @author Sergey Malenkov
 */

import java.util.ArrayList;
import java.util.List;

public final class java_util_ArrayList extends AbstractTest<List<String>> {
    public static void main(String[] args) {
        new java_util_ArrayList().test();
    }

    protected List<String> getObject() {
        List<String> list = new ArrayList<String>();
        list.add(null);
        list.add("string");
        return list;
    }

    protected List<String> getAnotherObject() {
        return new ArrayList<String>();
    }
}
