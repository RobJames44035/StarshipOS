/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.testng.annotations.Test;

/**
 * Test Duration.
 */
@Test
public class TestDuration extends AbstractTest {

    //-----------------------------------------------------------------------
    @Test
    public void test_immutable() {
        assertImmutable(Duration.class);
    }

    @Test
    public void plus_zeroReturnsThis() {
        Duration t = Duration.ofSeconds(-1);
        assertSame(t.plus(Duration.ZERO), t);
    }

    @Test
    public void plus_zeroSingleton() {
        Duration t = Duration.ofSeconds(-1);
        assertSame(t.plus(Duration.ofSeconds(1)), Duration.ZERO);
    }

    @Test
    public void plusSeconds_zeroReturnsThis() {
        Duration t = Duration.ofSeconds(-1);
        assertSame(t.plusSeconds(0), t);
    }

    @Test
    public void plusSeconds_zeroSingleton() {
        Duration t = Duration.ofSeconds(-1);
        assertSame(t.plusSeconds(1), Duration.ZERO);
    }

    @Test
    public void plusMillis_zeroReturnsThis() {
        Duration t = Duration.ofSeconds(-1, 2000000);
        assertSame(t.plusMillis(0), t);
    }

    @Test
    public void plusMillis_zeroSingleton() {
        Duration t = Duration.ofSeconds(-1, 2000000);
        assertSame(t.plusMillis(998), Duration.ZERO);
    }

    @Test
    public void plusNanos_zeroReturnsThis() {
        Duration t = Duration.ofSeconds(-1, 2000000);
        assertSame(t.plusNanos(0), t);
    }

    @Test
    public void plusNanos_zeroSingleton() {
        Duration t = Duration.ofSeconds(-1, 2000000);
        assertSame(t.plusNanos(998000000), Duration.ZERO);
    }

    @Test
    public void minus_zeroReturnsThis() {
        Duration t = Duration.ofSeconds(1);
        assertSame(t.minus(Duration.ZERO), t);
    }

    @Test
    public void minus_zeroSingleton() {
        Duration t = Duration.ofSeconds(1);
        assertSame(t.minus(Duration.ofSeconds(1)), Duration.ZERO);
    }

    @Test
    public void minusSeconds_zeroReturnsThis() {
        Duration t = Duration.ofSeconds(1);
        assertSame(t.minusSeconds(0), t);
    }

    @Test
    public void minusSeconds_zeroSingleton() {
        Duration t = Duration.ofSeconds(1);
        assertSame(t.minusSeconds(1), Duration.ZERO);
    }

    @Test
    public void minusMillis_zeroReturnsThis() {
        Duration t = Duration.ofSeconds(1, 2000000);
        assertSame(t.minusMillis(0), t);
    }

    @Test
    public void minusMillis_zeroSingleton() {
        Duration t = Duration.ofSeconds(1, 2000000);
        assertSame(t.minusMillis(1002), Duration.ZERO);
    }

    @Test
    public void minusNanos_zeroReturnsThis() {
        Duration t = Duration.ofSeconds(1, 2000000);
        assertSame(t.minusNanos(0), t);
    }

    @Test
    public void minusNanos_zeroSingleton() {
        Duration t = Duration.ofSeconds(1, 2000000);
        assertSame(t.minusNanos(1002000000), Duration.ZERO);
    }

    @Test
    public void test_abs_same() {
        Duration base = Duration.ofSeconds(12);
        assertSame(base.abs(), base);
    }

    void doTest_comparisons_Duration(Duration... durations) {
        for (int i = 0; i < durations.length; i++) {
            Duration a = durations[i];
            for (int j = 0; j < durations.length; j++) {
                Duration b = durations[j];
                if (i < j) {
                    assertEquals(a.compareTo(b)< 0, true, a + " <=> " + b);
                    assertEquals(a.equals(b), false, a + " <=> " + b);
                } else if (i > j) {
                    assertEquals(a.compareTo(b) > 0, true, a + " <=> " + b);
                    assertEquals(a.equals(b), false, a + " <=> " + b);
                } else {
                    assertEquals(a.compareTo(b), 0, a + " <=> " + b);
                    assertEquals(a.equals(b), true, a + " <=> " + b);
                }
            }
        }
    }

}
