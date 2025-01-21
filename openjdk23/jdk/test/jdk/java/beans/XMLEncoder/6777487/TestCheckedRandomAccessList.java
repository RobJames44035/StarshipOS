/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6777487
 * @summary Tests private field access for CheckedRandomAccessList
 * @run main TestCheckedRandomAccessList
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TestCheckedRandomAccessList {
    private static final Object OBJECT = new Object();

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        TestEncoder.test(
                Collections.checkedList(list, String.class),
                new ArrayList() {
                    private final Object type = OBJECT;
                },
                OBJECT
        );
    }
}
