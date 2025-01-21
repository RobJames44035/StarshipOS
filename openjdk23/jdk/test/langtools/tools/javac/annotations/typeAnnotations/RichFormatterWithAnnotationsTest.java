/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8144580
 * @summary java.lang.AssertionError: Missing type variable in where clause: T
 * @compile -Xlint:unchecked RichFormatterWithAnnotationsTest.java
 */

import java.lang.annotation.*;
import java.util.*;

public class RichFormatterWithAnnotationsTest {

    @Target({ElementType.TYPE_USE})
    @interface NonNull { }

    public static <T> void test() {
        final Collection<@NonNull T> c = new LinkedList<>();
        final List<@NonNull String> l = new LinkedList<@NonNull String>((Collection<@NonNull String>) c) {
            // empty
        };
    }
}
