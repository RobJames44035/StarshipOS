/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class DuplicateTypeParameter<T, T, A> {
    class Inner <P, P, Q> {}
    public void foo() {
        class Local <M, M, N> {};
    }
}

class Secondary<D, D, E> {}
