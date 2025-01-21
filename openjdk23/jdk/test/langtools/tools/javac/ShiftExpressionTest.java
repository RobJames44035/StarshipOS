/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * @test
 * @bug 4082814
 * @summary Constant shift expressions whose left operands were of type
 *          long were previously not evaluated correctly at compile-time.
 *          This is for the most part invisible, but it can be detected
 *          with the following test after 1.2beta1.
 * @author turnidge
 *
 * @compile ShiftExpressionTest.java
 * @run main ShiftExpressionTest
 */

public class ShiftExpressionTest {
    public static void main(String[] args) throws Exception {
        String s = "" + (0x0101L << 2) + (0x0101L >> 2) + (0x0101L >>> 2);
        if (s.indexOf("null",0) != -1) {
            throw new Exception(
                  "incorrect compile-time evaluation of shift");
        }
    }
}
