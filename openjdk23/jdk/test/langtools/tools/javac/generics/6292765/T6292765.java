/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     6292765
 * @summary NPE at Check.checkCompatibleConcretes
 * @author  Peter von der Ah\u00e9
 * @compile T6292765.java
 */

public class T6292765 {
    static class A<T> {}

    static class B<S> extends A< C<Object> > {}

    static class C<S> extends B<S> {}
}
