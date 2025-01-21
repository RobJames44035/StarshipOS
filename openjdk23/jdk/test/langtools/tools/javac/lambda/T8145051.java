/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8145051
 * @summary Wrong parameter name in synthetic lambda method leads to verifier error
 * @compile pkg/T8145051.java
 * @run main/othervm -Xverify:all T8145051
 */

public class T8145051 {

    public static void main(String [] args) {
        pkg.T8145051 t8145051 = new pkg.T8145051();
        t8145051.new Sub();
        if (!t8145051.s.equals("Executed lambda"))
            throw new AssertionError("Unexpected data");
        else
            System.out.println("OK");
    }

}
