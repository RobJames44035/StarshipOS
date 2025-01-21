/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8063054
 * @summary Bug summary
 * @compile -Werror -Xlint:rawtypes T8063054b.java
 */
class T8063054b {
    void test(Box<? extends Box<Number>> b) {
        Number n = b.<Number>map(Box::get).get();
    }

    interface Func<S,T> { T apply(S arg); }

    interface Box<T> {
        T get();
        <R> Box<R> map(Func<T,R> f);
    }
}
