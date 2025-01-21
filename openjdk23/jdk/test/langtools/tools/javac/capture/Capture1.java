/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5011312
 * @summary wildcard capture (snapshotting)
 * @author gafter
 *
 * @compile -Xlint:unchecked -Werror Capture1.java
 */

package capture1;

import java.util.List;

class C {
    public static <T> void copy1(List<? super T> dest, List<? extends T> src) {
        copy1(dest, src);
        copy2(dest, src); // oops
        copy3(dest, src); // oops
    }
    public static <T> void copy2(List<T> dest, List<? extends T> src) {
        copy1(dest, src);
        copy2(dest, src);
        copy3(dest, src); // oops
    }
    public static <T> void copy3(List<? super T> dest, List<T> src) {
        copy1(dest, src);
        copy2(dest, src); // oops
        copy3(dest, src);
    }
}
