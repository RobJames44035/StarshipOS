/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

// Test type mismatch on type argument for inner super constructor

class T<X> {

    class U<Y> extends T<X> {
        <B> U(B b) {
            <Object>super();
        }
        U(int i) {
            <Object>this("");
        }
    }

    class V<Z> extends U<Z> {
        <C> V(T<X> t) {
            t.<Integer>super("");
        }
    }

    <A> T() {
    }

    <K> void f() {
        this.<Object>f();
    }
}
