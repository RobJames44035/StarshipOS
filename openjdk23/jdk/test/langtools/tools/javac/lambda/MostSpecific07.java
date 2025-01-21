/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
import java.util.*;

class MostSpecific07 {

    interface Predicate<X, Y> {
        Y accept(X x);
    }

    interface VoidMapper {
        void accept();
    }

    interface ExtPredicate<X, Y> extends Predicate<X, Y> { }

    void test(boolean cond, ArrayList<String> als, VoidMapper vm) {
        m(u -> ()->{}, als, als, vm);
        m((u -> ()->{}), als, als, vm);
        m(cond ? u -> ()->{} : u -> ()->{}, als, als, vm);
    }

    <U, V> U m(Predicate<U, V> p, List<U> lu, ArrayList<U> au, V v) { return null; }

    <U, V> U m(ExtPredicate<U, V> ep, ArrayList<U> au, List<U> lu, V v) { return null; }
}
