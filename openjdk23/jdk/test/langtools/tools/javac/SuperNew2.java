/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4636022
 * @summary Test for a regression noted in an internal milestone.
 * @author gafter
 *
 * @compile SuperNew2.java
 * @run main SuperNew2
 */

public class SuperNew2 {
    int x;

    class Dummy {
        Dummy(Object o) {}
    }

    class Inside extends Dummy {
        Inside(final int y) {
            super(new Object() { int r = y; }); // ok
        }
    }

    public static void main(String[] args) {
        Object o = new SuperNew2().new Inside(12);
    }
}
