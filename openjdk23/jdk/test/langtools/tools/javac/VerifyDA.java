/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4836921
 * @summary javac (1.3.1 .. 1.4.2) generates invalid bytecode: def. ass. & finally w/ nested
 * @author gafter
 *
 * @compile VerifyDA.java
 * @run main VerifyDA
 */

public class VerifyDA {
    public static void main(String[] a) {
        String x;
        try {
            x = "hello";
        } finally {
            try {
                System.out.println("x");
            } catch (RuntimeException e) {
                e.printStackTrace();
                x = null;
            }
        }
        if (x != null) {
            System.out.println(x);
        }
    }
}
