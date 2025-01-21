/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     6391995
 * @summary removal of "rvalue conversion" causes problems
 * @author Peter von der Ah\u00e9
 * @compile T6391995.java
 */

public class T6391995 {
    <E> void iterate(Iterable<E> iterable) {
        Iterable<? extends Iterable<? extends Object>> x = null;
        iterate(x.iterator().next());
    }
}
