/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6978574
 * @summary  return statement in try block with multi-catch causes ClassFormatError
 */

public class T6978574  {
    static class A extends Exception { }
    static class B extends Exception { }

    static void foo() throws A { throw new A(); }
    static void bar() throws B { throw new B(); }

    static void test(boolean b) {
        try {
            if (b) foo(); else bar();
            return; // This should *not* cause ClassFormatError
        } catch (final A | B e ) { caught = true; }
        return;
    }

    static boolean caught = false;

    public static void main(String[] args) {
        test(true);
        if (!caught) throw new AssertionError();
        caught = false;
        test(false);
        if (!caught) throw new AssertionError();
    }
}
