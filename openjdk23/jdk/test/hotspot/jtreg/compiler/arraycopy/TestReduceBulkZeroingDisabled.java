/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8155241
 * @summary Test arraycopy elimination with ReduceBulkZeroing disabled.
 *
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -Xcomp -XX:-ReduceBulkZeroing
 *                   compiler.arraycopy.TestReduceBulkZeroingDisabled
 */

package compiler.arraycopy;

public class TestReduceBulkZeroingDisabled {

    static public void main(String[] args) {
        System.out.println("Passed");
    }
}

