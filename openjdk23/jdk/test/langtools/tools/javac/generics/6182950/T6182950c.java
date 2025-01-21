/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug     6182950
 * @summary methods clash algorithm should not depend on return type
 * @author  mcimadamore
 * @compile T6182950c.java
 */

class T6182950c {
    static abstract class A<X> {
        abstract Object m(X x);
    }

    static abstract class B<X> extends A<X> {
        Number m(X x) {return 0;}
    }

    final static class C<X> extends B<X> {
        Integer m(X x) {return 0;}
    }
}
