/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class MethodReference20<X> {

    MethodReference20(X x) { }

    interface SAM<Z> {
        MethodReference20<Z> m(Z z);
    }

    static void test(SAM<Integer> s) {   }

    public static void meth() {
        SAM<Integer> s = MethodReference20<String>::new;
        test(MethodReference20<String>::new);
    }
}
