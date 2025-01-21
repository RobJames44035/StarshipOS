/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8021204
 * @summary Test constructor BigInteger(String val, int radix) on very long string
 * @requires (sun.arch.data.model == "64" & os.maxMemory > 8g)
 * @run main/othervm -Xshare:off -Xmx8g StringConstructorOverflow
 * @author Dmitry Nadezhin
 */
import java.math.BigInteger;

public class StringConstructorOverflow {

    // String with hexadecimal value pow(2,pow(2,34))+1
    private static String makeLongHexString() {
        StringBuilder sb = new StringBuilder();
        sb.append('1');
        for (int i = 0; i < (1 << 30) - 1; i++) {
            sb.append('0');
        }
        sb.append('1');
        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            BigInteger bi = new BigInteger(makeLongHexString(), 16);
            if (bi.compareTo(BigInteger.ONE) <= 0) {
                throw new RuntimeException("Incorrect result " + bi.toString());
            }
        } catch (ArithmeticException e) {
            // expected
            System.out.println("Overflow is reported by ArithmeticException, as expected");
        } catch (OutOfMemoryError e) {
            // possible
            System.err.println("StringConstructorOverflow skipped: OutOfMemoryError");
            System.err.println("Run jtreg with -javaoption:-Xmx8g");
        }
    }
}
