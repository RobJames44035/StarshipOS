/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6371401
 * @summary Tests of shiftLeft and shiftRight on Integer.MIN_VALUE
 * @requires os.maxMemory >= 1g
 * @run main/othervm -Xmx512m ExtremeShiftingTests
 * @author Joseph D. Darcy
 */
import java.math.BigInteger;
import static java.math.BigInteger.*;

public class ExtremeShiftingTests {
    public static void main(String... args) {
        BigInteger bi = ONE.shiftLeft(Integer.MIN_VALUE);
        if (!bi.equals(ZERO))
            throw new RuntimeException("1 << " + Integer.MIN_VALUE);

        bi = ZERO.shiftLeft(Integer.MIN_VALUE);
        if (!bi.equals(ZERO))
            throw new RuntimeException("0 << " + Integer.MIN_VALUE);

        bi = BigInteger.valueOf(-1);
        bi = bi.shiftLeft(Integer.MIN_VALUE);
        if (!bi.equals(BigInteger.valueOf(-1)))
            throw new RuntimeException("-1 << " + Integer.MIN_VALUE);

        try {
            ONE.shiftRight(Integer.MIN_VALUE);
            throw new RuntimeException("1 >> " + Integer.MIN_VALUE);
        } catch (ArithmeticException ae) {
            ; // Expected
        }

        bi = ZERO.shiftRight(Integer.MIN_VALUE);
        if (!bi.equals(ZERO))
            throw new RuntimeException("0 >> " + Integer.MIN_VALUE);

        try {
            BigInteger.valueOf(-1).shiftRight(Integer.MIN_VALUE);
            throw new RuntimeException("-1 >> " + Integer.MIN_VALUE);
        } catch (ArithmeticException ae) {
            ; // Expected
        }

    }
}
