/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6835430
 * @summary 6835430: javac does not generate signature attributes for classes extending parameterized inner classes
 * @author mcimadamore
 *
 * @compile A.java
 * @compile T6835430.java
 */

class T6835430 {
    void test(B.D d) {
        B b = d.getT();
    }
}
