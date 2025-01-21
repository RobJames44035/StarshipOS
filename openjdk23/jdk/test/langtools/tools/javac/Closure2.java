/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4030374
 * @summary Initialization of up-level links, immediately after super(), occurs too late.
 * @author gafter
 *
 * @compile Closure2.java
 * @run main Closure2
 */

// Make sure the closure is present when the superclass is constructed.
// Specifically, inner2 must have its Closure2.this initialized when inner calls go().

public class Closure2 {
    private int mValue;

    public Closure2() {
        new inner2();
    }

    private abstract class inner {
        public inner() {
            go();
        }
        public abstract void go();
    }

    private class inner2 extends inner {
        public void go() {
            mValue = 2;
        }
    }

    public static void main(String args[]) {
        new Closure2();
    }
}
