/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     6476073
 * @summary Capture using super wildcard of type variables doesn't work
 * @compile T6476073.java
 */

import java.util.Collection;
import java.util.List;

public class T6476073 {
    public static <B> void m(List<? super B> list,Collection<? super B> coll) {
        m(list,coll);
    }
}
