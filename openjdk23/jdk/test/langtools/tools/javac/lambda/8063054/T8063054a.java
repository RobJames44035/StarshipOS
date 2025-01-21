/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8063054
 * @summary Bug summary
 * @compile -Werror -Xlint:rawtypes T8063054a.java
 */
class T8063054a {
    interface Consumer<T> { void accept(T arg); }
    interface Parent<P> { void foo(); }
    interface Child extends Parent<String> {}

    static <T> void m(T arg, Consumer<T> f) {}

    public void test(Child c) { m(c, Parent::foo); }
}
