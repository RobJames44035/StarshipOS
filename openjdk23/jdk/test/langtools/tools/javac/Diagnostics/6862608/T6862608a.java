/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */


import java.util.*;

class T6862608a {

    <T> Comparator<T> compound(Iterable<? extends Comparator<? super T>> it) {
        return null;
    }

    public void test(List<Comparator<?>> x) {
        Comparator<String> c3 = compound(x);
    }
}
