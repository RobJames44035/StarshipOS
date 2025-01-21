/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8154587
 * @summary Check that invokeinterface of clone() works properly where clone() is
 *          an abstract method in a superinterface and also overridden locally.
 * @run main AbstractClone
 */

public class AbstractClone {

    interface I1 {
        Object clone();
    }

    interface I2 extends I1 { }

    static class C implements I2 {
        public Object clone() {
            return "In C's clone()";
        }
    }

    static Object test(I2 i) { return i.clone(); }

    public static void main(String[] args) {
        String s = (String)test(new C());
        if (!s.equals("In C's clone()")) {
            throw new RuntimeException("Wrong clone() called");
        }
    }
}
