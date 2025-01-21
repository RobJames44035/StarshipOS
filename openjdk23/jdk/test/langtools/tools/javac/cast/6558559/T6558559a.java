/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6558559
 * @summary Extra "unchecked" diagnostic
 * @author Maurizio Cimadamore
 *
 * @compile T6558559a.java -Xlint:unchecked -Werror
 */

class T6558559a {
    interface A<T> {}

    static class B<T> {}

    A<?> x = null;
    B<?> y = (B<?>)x;
}
