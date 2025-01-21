/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4257961
 * @summary Check that reference to parameter from within local class is compiled correctly.
 * @author maddox
 *
 * @compile OuterParameter_1.java
 * @run main OuterParameter_1
 */

public class OuterParameter_1 {
    void f(final int i) throws Exception {
        class A {
            A() throws Exception {
                class B {
                    B() throws Exception {
                        if (i != 555) throw new Exception();
                    }
                }
            }
        }
    }

    public static void main(String args[]) throws Exception {
        new OuterParameter_1().f(555);
    }
}
