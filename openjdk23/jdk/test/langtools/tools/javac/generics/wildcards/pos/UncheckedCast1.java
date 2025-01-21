/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4875036
 * @summary generics: failure of some unchecked casts
 * @author gafter
 *
 * @compile  UncheckedCast1.java
 */

class Z {
    <U, T extends U> T g(Object o) {
        return (T) o; // bug???
    }
}
