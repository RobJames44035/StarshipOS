/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time;

import static org.testng.Assert.assertSame;

import java.util.Set;
import java.time.ZoneOffset;

import org.testng.annotations.Test;

/**
 * Test ZoneOffset.
 */
@Test
public class TestZoneOffset extends AbstractTest {

    @Test
    public void test_immutable() {
        assertImmutable(ZoneOffset.class, /* ignore field */ Set.of("rules"));
    }

    @Test
    public void test_factory_ofTotalSecondsSame() {
        assertSame(ZoneOffset.ofTotalSeconds(0), ZoneOffset.UTC);
    }

}
