/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8057758
 * @summary MultiplyToLen sets its return type to have a bottom offset which confuses code generation
 *
 * @run main/othervm -XX:-TieredCompilation -XX:-BackgroundCompilation -XX:-UseOnStackReplacement
 *      -XX:TypeProfileLevel=222
 *      compiler.intrinsics.bigInteger.TestMultiplyToLenReturnProfile
 */

package compiler.intrinsics.bigInteger;

import java.math.BigInteger;

public class TestMultiplyToLenReturnProfile {

    static BigInteger m(BigInteger i1, BigInteger i2) {
        BigInteger res = BigInteger.valueOf(0);
        for (int i = 0; i < 100; i++) {
            res.add(i1.multiply(i2));
        }
        return res;
    }

    static public void main(String[] args) {
        BigInteger v = BigInteger.valueOf(Integer.MAX_VALUE).pow(2);
        for (int i = 0; i < 20000; i++) {
            m(v, v.add(BigInteger.valueOf(1)));
        }
    }
}
