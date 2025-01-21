/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8071291
 * @summary Compiler crashes trying to cast UnionType to IntersectionClassType
 * @compile T8071291.java
 */

class T8071291 {

    interface A { }
    class Exception1 extends Exception implements A { }
    class Exception2 extends Exception implements A { }

    void test(boolean cond) {
        try {
            if (cond) {
                throw new Exception1();
            } else {
                throw new Exception2();
            }
        }
        catch (Exception1|Exception2 x) {
            if (x instanceof Exception1) { }
        }
    }
}
