/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7192246
 * @summary qualified 'this' inside default method causes StackOverflowException
 * @compile Pos13.java
 */

public class Pos13 {

    static int assertionCount = 0;

    static void assertTrue(boolean cond) {
        assertionCount++;
        if (!cond)
            throw new AssertionError();
    }

    interface Outer {
        abstract void doSomething();

        default void m() {
            new SubOuter() {
                public void doSomething() {
                    Outer.this.doSomething();
                }
            }.doSomething();
        }
    }

    interface SubOuter extends Outer { }

    static class E implements Outer {
        public void doSomething() { assertTrue(true); }
    }

    public static void main(String[] args) {
        new E().m();
        assertTrue(assertionCount == 1);
    }
}
