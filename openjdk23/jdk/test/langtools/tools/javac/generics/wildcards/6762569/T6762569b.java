/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
import java.util.*;

class T6762569b {
    <T> void m(T t, List<? super List<T>> list) {}

    void test(List<? super List<? extends Number>> list) {
        m("", list);
    }
}
