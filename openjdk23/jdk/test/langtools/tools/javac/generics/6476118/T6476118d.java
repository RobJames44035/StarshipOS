/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6476118
 * @summary compiler bug causes runtime ClassCastException for generics overloading
 * @compile T6476118d.java
 */

class T6476118d {
    int m = 3;

    interface m { }

    int m () {
        return m;
    }
}
