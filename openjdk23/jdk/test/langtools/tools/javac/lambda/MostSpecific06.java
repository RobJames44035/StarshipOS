/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
import java.util.*;

class MostSpecific06 {

    interface Predicate<X> {
        boolean accept(X x);
    }

    interface ExtPredicate<X> extends Predicate<X> { }



    void test(boolean cond, ArrayList<String> als) {
        m(u -> true, als, als);
        m((u -> true), als, als);
        m(cond ? u -> true : u -> false, als, als);
    }

    <U> U m(Predicate<U> p, List<U> lu, ArrayList<U> au) { return null; }


    <U> U m(ExtPredicate<U> ep, ArrayList<U> au, List<U> lu) { return null; }
}
