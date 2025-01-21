/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @summary null test
 * @build KullaTesting TestingInputStream
 * @run testng NullTest
 */

import org.testng.annotations.Test;

@Test
public class NullTest extends KullaTesting {

    public void testNull() {
        assertEval("null;", "null");
        assertEval("(Object)null;", "null");
        assertEval("(String)null;", "null");
    }
}
