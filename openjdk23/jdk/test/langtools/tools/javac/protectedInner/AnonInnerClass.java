/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/**
 * @test
 * @bug 4251061
 * @summary Verify that we can access inherited, protected method from
 * an anonymous enclosing superclass.
 *
 * @run compile AnonInnerClass.java
 */

import java.util.Vector; // example superclass with protected method

public class AnonInnerClass extends Vector {

    public static void main(String[] args) {
        new AnonInnerClass().test();
    }

    public void test() {
        Runnable r = new Runnable() {
            public void run() {
                // call protected method of enclosing class' superclass
                AnonInnerClass.this.removeRange(0,0);
            }
        };
    }
}
