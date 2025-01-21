/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4899722
 * @summary Basic tests of scaleByPowerOfTen
 * @author Joseph D. Darcy
 */

import java.math.*;

public class ScaleByPowerOfTenTests {

    public static void main(String argv[]) {
        for (int i = -10; i < 10; i++) {
            BigDecimal bd = BigDecimal.ONE.scaleByPowerOfTen(i);
            BigDecimal expected;

            if (!bd.equals(expected = new BigDecimal(BigInteger.ONE, -i))) {
                throw new RuntimeException("Unexpected result " +
                                           bd.toString() +
                                           "; expected " +
                                           expected.toString());
            }

            bd = BigDecimal.ONE.negate().scaleByPowerOfTen(i);
            if (!bd.equals(expected = new BigDecimal(BigInteger.ONE.negate(), -i))) {
                throw new RuntimeException("Unexpected result " +
                                           bd.toString() +
                                           "; expected " +
                                           expected.toString());
            }


        }
    }
}
