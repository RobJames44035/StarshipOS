/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 7003744 6999622
 * @summary Compiler error concerning final variables
 * @author mcimadamore
 *
 * @compile T7003744b.java
 */

class T7003744b {
    void test() {
        final int bogus;

        for (int i1 = 0, i2 = 2; i1 < i2; i1++) {
         final int i_1 = 2;
        }
        for (Object o : new Object[] { null }) {
         final int i_2 = 2;
        }

        bogus = 4;
    }
}
