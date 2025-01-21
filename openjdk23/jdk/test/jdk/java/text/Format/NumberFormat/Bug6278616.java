/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @summary Confirm that AtomicInteger and AtomicLong are formatted correctly.
 *          That is, make sure they are not treated as a double when formatted
 *          anymore (which can result in the loss of precision).
 * @bug 6278616
 * @run junit Bug6278616
 */

import java.text.NumberFormat;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Bug6278616 {

    private static final NumberFormat nf = NumberFormat.getInstance();

    // Test that NumberFormat formats numerically equivalent int
    // and AtomicInteger values the same
    @ParameterizedTest
    @MethodSource("ints")
    public void formattedAtomicIntTest(int testInt) {
        String formattedInt = nf.format(testInt);
        String formattedAtomicInt = nf.format(new AtomicInteger(testInt));
        assertEquals(formattedAtomicInt, formattedInt, "Formatting numerically" +
                " equivalent AtomicInteger and int should produce the same String value");
    }

    // Various int values
    private static int[] ints() {
        return new int[] { Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
    }

    // Test that NumberFormat formats numerically equivalent long
    // and AtomicLong values the same
    @ParameterizedTest
    @MethodSource("longs")
    public void formattedAtomicLongTest(long testLong) {
        String formattedLong = nf.format(testLong);
        String formattedAtomicLong = nf.format(new AtomicLong(testLong));
        assertEquals(formattedAtomicLong, formattedLong, "Formatting numerically" +
                " equivalent AtomicLong and long should produce the same String value");
    }

    // Various long values
    private static long[] longs() {
        return new long[] { Long.MIN_VALUE, -1, 0, 1, Long.MAX_VALUE};
    }
}
