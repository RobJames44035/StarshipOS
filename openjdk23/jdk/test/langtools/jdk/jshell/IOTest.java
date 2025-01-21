/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Test input/output
 * @build KullaTesting TestingInputStream
 * @run testng IOTest
 */

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test
public class IOTest extends KullaTesting {

    String LINE_SEPARATOR = System.getProperty("line.separator");

    public void testOutput() {
        assertEval("System.out.println(\"Test\");");
        assertEquals(getOutput(), "Test" + LINE_SEPARATOR);
    }

    public void testErrorOutput() {
        assertEval("System.err.println(\"Oops\");");
        assertEquals(getErrorOutput(), "Oops" + LINE_SEPARATOR);
    }

    public void testInput() {
        setInput("x");
        assertEval("(char)System.in.read();", "'x'");
    }
}
