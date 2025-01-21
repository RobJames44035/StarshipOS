/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4478838 4533580
 * @summary Check correct handling of DU in assert statements
 * @author Neal Gafter (gafter)
 *
 * @run compile DUAssert.java
 */

class DUSwitch {
    void foo() {
        final int i;
        assert true : i=3;
        i=4;
    }
    void bar(boolean b) {
        final int i;
        assert b : i=3;
        i=4;
    }
    void baz() {
        final int i;
        assert false : i=3;
        i=4;
    }
}
