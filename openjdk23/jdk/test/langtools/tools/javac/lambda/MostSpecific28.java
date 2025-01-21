/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
class MostSpecific28 {

    interface Pred<T> { boolean test(T arg); }
    interface Fun<T,R> { R apply(T arg); }

    static void m1(Pred<? super Integer> f) {}
    static void m1(Fun<Number, Boolean> f) {}

    void test() {
        m1((Number n) -> true);
    }

}
