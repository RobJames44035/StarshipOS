/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8141613
 * @summary Compiler fails to infer generic type
 * @compile T8141613.java
 */
class T8141613 {

    static class Sub<V, L> implements Sup<V, L> {
        Sub(Bar<V> barv) { }
    }

    interface Bar<V> { }

    interface Sup<V, L> { }

    <L> void test() {
        Sup<?, L> res1 = makeSub(makeBar());
        Sup<?, L> res2 = new Sub<>(makeBar());
    }

    <S, U> Sub<S, U> makeSub(Bar<S> bars) { return null; }

    <B> Bar<?> makeBar() {
        return null;
    }
}
