/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time;

import java.time.OffsetTime;

import org.testng.annotations.Test;

/**
 * Test OffsetTime.
 */
@Test
public class TestOffsetTime extends AbstractTest {

    @Test
    public void test_immutable() {
        assertImmutable(OffsetTime.class);
    }

}
