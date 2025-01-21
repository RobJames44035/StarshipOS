/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 6735320
 * @summary javadoc throws exception if serialField value is missing
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main T6735320
 */

import javadoc.tester.JavadocTester;

public class T6735320 extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new T6735320();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                testSrc("SerialFieldTest.java"));
        checkExit(Exit.ERROR);
        checkOutput(Output.STDERR, false,
                "OutOfBoundsException");
    }
}
