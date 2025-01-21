/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
class MostSpecific14 {
    interface ToNumber { Number get(); }
    interface ToToNumber { ToNumber get(); }
    interface Factory<T> { T get(); }

    void m1(Factory<Factory<Object>> f) {}
    void m1(ToToNumber f) {}

    void m2(Factory<Factory<Number>> f) {}
    void m2(ToToNumber f) {}

    void m3(Factory<Factory<Integer>> f) {}
    void m3(ToToNumber f) {}


    void test() {
        m1(() -> () -> 23); // ok: choose ToToNumber
        m2(() -> () -> 23); // error: ambiguous
        m3(() -> () -> 23); // ok: choose Factory<Factory<Integer>>

        m1(() -> this::getInteger); // ok: choose ToToNumber
        m2(() -> this::getInteger); // error: ambiguous
        m3(() -> this::getInteger); // ok: choose Factory<Factory<Integer>>
    }

    Integer getInteger() { return 23; }
}