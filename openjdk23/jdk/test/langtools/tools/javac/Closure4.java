/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4030374
 * @summary Initialization of up-level links, immediately after super(), occurs too late.
 * @author gafter
 *
 * @compile Closure4.java
 * @run main Closure4
 */

// Make sure the closure is present when the superclass is constructed.
// Specifically, Closure4.$1 must have its Closure4.this initialized when Inner calls foo().

public class Closure4 {

    public int v;

    public Closure4() {
        v = 0;

        Inner i = new Inner() {
            public void foo() {
                if (v != 0) throw new Error();
            }
        };

        i.foo();
    }

    public static void main(String[] arg) {
        new Closure4();
    }
}

class Inner {
    Inner() {
        foo();
    }

    public void foo() { throw new Error(); }
}
