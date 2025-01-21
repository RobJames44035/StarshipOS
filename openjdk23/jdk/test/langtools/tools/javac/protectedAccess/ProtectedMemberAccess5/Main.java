/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4786115
 * @summary (cl) Odd IllegalAccessError across jar-file boundaries
 * @author gafter
 *
 * @compile z1/Z1.java z2/Z2.java
 * @run main z1.Z1
 */

class Main {
    // this source file is not compiled for this test.
}
