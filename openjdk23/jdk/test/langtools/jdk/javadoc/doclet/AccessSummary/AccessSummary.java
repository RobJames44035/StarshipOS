/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug      4637604 4775148 8183037 8182765 8196202
 * @summary  Test the tables for summary attribute
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build    javadoc.tester.*
 * @run main AccessSummary
 */

import javadoc.tester.JavadocTester;

public class AccessSummary extends JavadocTester {
    /**
     * The entry point of the test.
     * @param args the array of command line arguments.
     * @throws Exception if the test fails
     */
    public static void main(String... args) throws Exception {
        var tester = new AccessSummary();
        tester.runTests();
    }

    @Test
    public void testAccessSummary() {
        javadoc("-d", "out",
                "-sourcepath", testSrc,
                "p1", "p2");
        checkExit(Exit.OK);
        checkSummary(false);
    }

    void checkSummary(boolean found) {
        checkOutput("index.html", found,
                 """
                     summary="Package Summary table, listing packages, and an explanation\"""");

        // Test that the summary attribute appears or not
        checkOutput("p1/C1.html", found,
                 """
                     summary="Constructor Summary table, listing constructors, and an explanation\"""");

        // Test that the summary attribute appears or not
        checkOutput("constant-values.html", found,
                 """
                     summary="Constant Field Values table, listing constant fields, and values\"""");
    }
}
