/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

// Test type mismatch on type argument for super constructor

class T<X> {

    class U<Y> extends T<X> {
        <B> U() {
            <Integer>super("");
        }
        U(int i) {
            <Object>this();
        }
    }

    class V<Z> extends U<Z> {
        <C> V(T<X> t) {
            t.<Object>super();
        }
    }

    <A> T(A a) {
    }

    <K> void f() {
        this.<Object>f();
    }
}
