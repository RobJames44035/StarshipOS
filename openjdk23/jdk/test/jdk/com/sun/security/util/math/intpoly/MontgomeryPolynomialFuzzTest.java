/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.util.Random;
import sun.security.util.math.IntegerMontgomeryFieldModuloP;
import sun.security.util.math.ImmutableIntegerModuloP;
import java.math.BigInteger;
import sun.security.util.math.intpoly.*;

/*
 * @test
 * @key randomness
 * @modules java.base/sun.security.util java.base/sun.security.util.math
 *          java.base/sun.security.util.math.intpoly
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:-UseIntPolyIntrinsics
 *      MontgomeryPolynomialFuzzTest
 * @summary Unit test MontgomeryPolynomialFuzzTest.
 */

/*
 * @test
 * @key randomness
 * @modules java.base/sun.security.util java.base/sun.security.util.math
 *          java.base/sun.security.util.math.intpoly
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+UseIntPolyIntrinsics
 *      MontgomeryPolynomialFuzzTest
 * @summary Unit test MontgomeryPolynomialFuzzTest.
 */

// This test case is NOT entirely deterministic, it uses a random seed for pseudo-random number generator
// If a failure occurs, hardcode the seed to make the test case deterministic
public class MontgomeryPolynomialFuzzTest {
    public static void main(String[] args) throws Exception {
        // Note: it might be useful to increase this number during development
        final int repeat = 1000000;
        for (int i = 0; i < repeat; i++) {
            run();
        }
        System.out.println("Fuzz Success");
    }

    private static void check(BigInteger reference,
            ImmutableIntegerModuloP testValue, long seed) {
        if (!reference.equals(testValue.asBigInteger())) {
            throw new RuntimeException("SEED: " + seed);
        }
    }

    public static void run() throws Exception {
        Random rnd = new Random();
        long seed = rnd.nextLong();
        rnd.setSeed(seed);

        IntegerMontgomeryFieldModuloP montField = MontgomeryIntegerPolynomialP256.ONE;
        BigInteger P = MontgomeryIntegerPolynomialP256.ONE.MODULUS;
        BigInteger r = BigInteger.ONE.shiftLeft(260).mod(P);
        BigInteger rInv = r.modInverse(P);
        BigInteger aRef = (new BigInteger(P.bitLength(), rnd)).mod(P);

        // Test conversion to montgomery domain
        ImmutableIntegerModuloP a = montField.getElement(aRef);
        aRef = aRef.multiply(r).mod(P);
        check(aRef, a, seed);

        if (rnd.nextBoolean()) {
            aRef = aRef.multiply(aRef).multiply(rInv).mod(P);
            a = a.multiply(a);
            check(aRef, a, seed);
        }

        if (rnd.nextBoolean()) {
            aRef = aRef.add(aRef).mod(P);
            a = a.add(a);
            check(aRef, a, seed);
        }
    }
}

//make test TEST="test/jdk/com/sun/security/util/math/intpoly/MontgomeryPolynomialFuzzTest.java"
