/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * @test
 * @bug 1227855
 * @summary The order of initialization used to be inconsistent, depending
 *          on whether a value was initialized to its default value.
 * @author turnidge
 *
 * @compile IllDefinedOrderOfInit.java
 * @run main IllDefinedOrderOfInit
 */

public class IllDefinedOrderOfInit {
    int i = m();
    int j = 0;
    IllDefinedOrderOfInit() {
        if (j != 0) {
            throw new Error();
        }
    }
    int m() { j = 5; return j++; }
    static public void main(String args[]) {
        new IllDefinedOrderOfInit();
    }
}
