/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
 * @bug 4181191 8215759
 * @summary test BigInteger modPow method (use -Dseed=X to set PRNG seed)
 * @library /test/lib
 * @build jdk.test.lib.RandomFactory
 * @run main ModPow
 * @key randomness
 */
import java.math.BigInteger;
import java.util.Random;
import jdk.test.lib.RandomFactory;

public class ModPow {
    public static void main(String[] args) {
        Random rnd = RandomFactory.getRandom();

        for (int i=0; i<2000; i++) {
            // Clamp random modulus to a positive value or modPow() will
            // throw an ArithmeticException.
            BigInteger m = new BigInteger(800, rnd).max(BigInteger.ONE);
            BigInteger base = new BigInteger(16, rnd);
            if (rnd.nextInt() % 1 == 0)
                base = base.negate();
            BigInteger exp = new BigInteger(8, rnd);

            BigInteger z = base.modPow(exp, m);
            BigInteger w = base.pow(exp.intValue()).mod(m);
            if (!z.equals(w)){
                System.err.println(base +" ** " + exp + " mod "+ m);
                System.err.println("modPow : " + z);
                System.err.println("pow.mod: " + w);
                throw new RuntimeException("BigInteger modPow failure.");
            }
        }
    }
}
