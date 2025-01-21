/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

package com.sun.hotspot.igv.data;

import org.junit.*;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Thomas Wuerthinger
 */
public class ChangedEventTest {

    public ChangedEventTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addListener method, of class Event.
     */
    @Test
    public void testBase() {

        ChangedEvent<Integer> e = new ChangedEvent<>(5);
        final int[] fireCount = new int[1];

        e.addListener(new ChangedListener<Integer>() {
            @Override
            public void changed(Integer s) {
                assertEquals(s.intValue(), 5);
                fireCount[0]++;
            }
        });

        e.fire();
        assertEquals(1, fireCount[0]);

        e.fire();
        assertEquals(2, fireCount[0]);

        e.beginAtomic();

        e.fire();
        assertEquals(2, fireCount[0]);

        e.fire();
        assertEquals(2, fireCount[0]);

        e.fire();
        assertEquals(2, fireCount[0]);

        e.endAtomic();
        assertEquals(3, fireCount[0]);

    }


}
