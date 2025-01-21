/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5008227
 * @summary Make sure that changes to the Date class don't break java.sql.Timestamp.
 * @modules java.sql
 * @run junit TimestampTest
 */

import java.util.*;
import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class TimestampTest {

    /**
     * 5008227: java.sql.Timestamp.after() is not returning correct result
     *
     * Test before(), after(), equals(), compareTo() and getTime().
     */
    @Test
    public void Test5008227() {
        long t = System.currentTimeMillis();
        Timestamp ts1 = new Timestamp(t), ts2 = new Timestamp(t);
        ts1.setNanos(999999999);
        ts2.setNanos(  1000000);
        compareTimestamps(ts1, ts2, 1);

        ts1.setTime(t + 1000);
        ts2.setTime(t);
        ts1.setNanos(   999999);
        ts2.setNanos(999999999);
        compareTimestamps(ts1, ts2, 1);

        ts1.setTime(t);
        ts2.setTime(t);
        ts1.setNanos(123456789);
        ts2.setNanos(123456789);
        compareTimestamps(ts1, ts2, 0);

        ts1.setTime(t);
        ts2.setTime(t);
        ts1.setNanos(1);
        ts2.setNanos(2);
        compareTimestamps(ts1, ts2, -1);

        ts1.setTime(t);
        ts2.setTime(t+1000);
        ts1.setNanos(999999);
        ts2.setNanos(     0);
        compareTimestamps(ts1, ts2, -1);
    }

    /**
     * Compares two Timestamps with the expected result.
     *
     * @param ts1 the first Timestamp
     * @param ts2 the second Timestamp
     * @param expect the expected relation between ts1 and ts2; 0 if
     * ts1 equals to ts2, or 1 if ts1 is after ts2, or -1 if ts1 is
     * before ts2.
     */
    private void compareTimestamps(Timestamp ts1, Timestamp ts2, int expected) {
        boolean expectedResult = expected > 0;
        boolean result = ts1.after(ts2);
        if (result != expectedResult) {
            fail("ts1.after(ts2) returned " + result
                  + ". (ts1=" + ts1 + ", ts2=" + ts2 + ")");
        }

        expectedResult = expected < 0;
        result = ts1.before(ts2);
        if (result != expectedResult) {
            fail("ts1.before(ts2) returned " + result
                  + ". (ts1=" + ts1 + ", ts2=" + ts2 + ")");
        }

        expectedResult = expected == 0;
        result = ts1.equals(ts2);
        if (result != expectedResult) {
            fail("ts1.equals(ts2) returned " + result
                  + ". (ts1=" + ts1 + ", ts2=" + ts2 + ")");
        }

        int x = ts1.compareTo(ts2);
        int y = (x > 0) ? 1 : (x < 0) ? -1 : 0;
        if (y != expected) {
            fail("ts1.compareTo(ts2) returned " + x + ", expected "
                  + relation(expected, "") + "0"
                  + ". (ts1=" + ts1 + ", ts2=" + ts2 + ")");
        }
        long t1 = ts1.getTime();
        long t2 = ts2.getTime();
        int z = (t1 > t2) ? 1 : (t1 < t2) ? -1 : 0;
        if (z == 0) {
            int n1 = ts1.getNanos();
            int n2 = ts2.getNanos();
            z = (n1 > n2) ? 1 : (n1 < n2) ? -1 : 0;
        }
        if (z != expected) {
            fail("ts1.getTime() " + relation(z, "==") + " ts2.getTime(), expected "
                  + relation(expected, "==")
                  + ". (ts1=" + ts1 + ", ts2=" + ts2 + ")");
        }
    }

    private static String relation(int x, String whenEqual) {
        return (x > 0) ? ">" : (x < 0) ? "<" : whenEqual;
    }
}
