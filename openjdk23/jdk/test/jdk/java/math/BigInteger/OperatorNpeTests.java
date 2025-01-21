/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6365176
 * @summary Get NullPointerExceptions when expected
 * @author Joseph D. Darcy
 */

import java.math.*;
import static java.math.BigInteger.*;

public class OperatorNpeTests {

    public static void main(String... argv) {
        BigInteger[] specialValues = {ZERO, ONE, TEN};

        for (BigInteger bd : specialValues) {
            BigInteger result;
            try {
                result = bd.multiply(null);
                throw new RuntimeException("Instead of NPE got " + result);
            } catch (NullPointerException npe) {
                ; // Expected
            }

            try {
                result = bd.divide(null);
                throw new RuntimeException("Instead of NPE got " + result);
            } catch (NullPointerException npe) {
                ; // Expected
            }

            try {
                result = bd.add(null);
                throw new RuntimeException("Instead of NPE got " + result);
            } catch (NullPointerException npe) {
                ; // Expected
            }

            try {
                result = bd.subtract(null);
                throw new RuntimeException("Instead of NPE got " + result);
            } catch (NullPointerException npe) {
                ; // Expected
            }


        }
    }
}
