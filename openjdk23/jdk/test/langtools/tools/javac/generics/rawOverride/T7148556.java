/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7148556
 * @summary Implementing a generic interface causes a public clone() to become inaccessible
 * @compile T7148556.java
 */

class T7148556 {

    interface A extends Cloneable {
       public Object clone();
    }

    interface B extends A, java.util.List { }

    void test(B b) {
        b.clone();
    }
}
