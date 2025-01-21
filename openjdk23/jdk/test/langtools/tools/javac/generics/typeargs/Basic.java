/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4851039
 * @summary explicit type arguments
 * @author gafter
 *
 * @compile  Basic.java
 */

// Test all of the basic forms for explicit type arguments

class T<X> {

    class U<Y> extends T<X> {
        <B> U() {
            <Object>super();
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

    <A> T() {
    }

    <K> void f() {
        this.<Object>f();
    }

    public static void main(String[] args) {
        T<Integer> x = new <Object>T<Integer>();
        T<Integer>.U<Float> y = x.new <Object>U<Float>();
        x.<Object>f();
    }
}
