/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/**
 * @test
 * @bug 7005594
 * @summary Array overflow not handled correctly with loop optimzations
 *
 * @run main/othervm -Xcomp
                     -XX:CompileOnly=compiler.c2.Test7005594::test
                     compiler.c2.Test7005594
 */

package compiler.c2;

public class Test7005594 {
    static int test(byte a[]){
        int result = 0;
        for (int i = 1; i < a.length; i += Integer.MAX_VALUE) {
            result += a[i];
        }
        return result;
    }

    public static void main(String [] args){
        try {
            int result = test(new byte[2]);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException was not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Expected " + e + " was thrown");
        }
    }
}
