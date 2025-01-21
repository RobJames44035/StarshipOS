/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class T6207386 {
    static class F<T> {}
    static class C<X extends F<F<? super X>>> {
        C(X x) {
            F<? super X> f = x;
        }
    }
}
