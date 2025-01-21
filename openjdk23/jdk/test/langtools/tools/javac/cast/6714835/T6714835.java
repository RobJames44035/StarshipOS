/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.util.*;

class T6714835 {
    void cast1(Iterable<? extends Integer> x) {
        Collection<? extends Number> x1 = (Collection<? extends Number>)x; //ok
        Collection<? super Integer> x2 = (Collection<? super Integer>)x; //warn
    }

    void cast2(Iterable<? super Number> x) {
        Collection<? super Integer> x1 = (Collection<? super Integer>)x; //ok
        Collection<? extends Number> x2 = (Collection<? extends Number>)x; //warn
    }
}
