/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test
 * @bug 4059492
 * @summary The compiler crashed when it encountered inner classes inside
 *          of dead code which contained uplevel references.
 * @author turnidge
 *
 * @compile DeadInnerClass.java
 */

class DeadInnerClass {
    public String val = "test value";

    void method() {
        if (false) {
            // Dead code.
            class Inner {
                Inner() {
                    System.out.println(val);
                }
            }

            new DeadInnerClass();
        }
    }
}
