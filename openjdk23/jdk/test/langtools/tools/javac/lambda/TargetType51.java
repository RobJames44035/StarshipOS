/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 8005244
 * @summary Implement overload resolution as per latest spec EDR
 *          smoke test for combinator-like stuck analysis
 * @author  Maurizio Cimadamore
 * @compile TargetType51.java
 */

import java.util.Comparator;

class TargetType51 {

    interface SimpleMapper<T, U> {
       T map(U t);
    }

    interface SimpleList<X> {
        SimpleList<X> sort(Comparator<? super X> c);
    }

    static class Person {
        String getName() { return ""; }
    }

    <T, U extends Comparable<? super U>> Comparator<T> comparing(SimpleMapper<U, T> mapper) {  return null; }

    static class F<U extends Comparable<? super U>, T> {
        F(SimpleMapper<U, T> f) { }
    }

    void testAssignmentContext(SimpleList<Person> list, boolean cond) {
        SimpleList<Person> p1 = list.sort(comparing(Person::getName));
        SimpleList<Person> p2 = list.sort(comparing(x->x.getName()));
        SimpleList<Person> p3 = list.sort(cond ? comparing(Person::getName) : comparing(x->x.getName()));
        SimpleList<Person> p4 = list.sort((cond ? comparing(Person::getName) : comparing(x->x.getName())));
    }

    void testMethodContext(SimpleList<Person> list, boolean cond) {
        testMethodContext(list.sort(comparing(Person::getName)), true);
        testMethodContext(list.sort(comparing(x->x.getName())), true);
        testMethodContext(list.sort(cond ? comparing(Person::getName) : comparing(x->x.getName())), true);
        testMethodContext(list.sort((cond ? comparing(Person::getName) : comparing(x->x.getName()))), true);
    }
}
