/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4661481 8024096
 * @summary This test determines if the value of the -encoding option is
 * properly passed from Javadoc to the source file parser.
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main TestEncoding
 */

import javadoc.tester.JavadocTester;

public class TestEncoding extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestEncoding();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                "-sourcepath", testSrc,
                "-encoding", "iso-8859-1",
                testSrc("EncodeTest.java"));
        checkExit(Exit.OK);

        // If ??? is found in the output, the source file was not read with the correct encoding setting.
        checkOutput("EncodeTest.html", false,
                "??");
    }
}
