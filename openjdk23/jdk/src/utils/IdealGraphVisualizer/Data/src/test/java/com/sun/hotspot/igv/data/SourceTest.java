/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package com.sun.hotspot.igv.data;

import java.util.Arrays;
import java.util.LinkedHashSet;
import org.junit.*;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Thomas
 */
public class SourceTest {

    public SourceTest() {
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
     * Test of getSourceNodes method, of class Source.
     */
    @Test
    public void testBase() {
        final Source s = new Source();

        final InputNode N1 = new InputNode(1);
        final InputNode N2 = new InputNode(2);

        s.addSourceNode(N1);
        assertEquals(s.getSourceNodes(), Arrays.asList(N1));
        assertEquals(s.getSourceNodesAsSet(), new LinkedHashSet<>(Arrays.asList(1)));

        s.addSourceNode(N2);
        assertEquals(s.getSourceNodes(), Arrays.asList(N1, N2));
        assertEquals(s.getSourceNodesAsSet(), new LinkedHashSet<>(Arrays.asList(1, 2)));

        s.addSourceNode(N1);
        assertEquals(s.getSourceNodes(), Arrays.asList(N1, N2));
        assertEquals(s.getSourceNodesAsSet(), new LinkedHashSet<>(Arrays.asList(1, 2)));
    }
}
