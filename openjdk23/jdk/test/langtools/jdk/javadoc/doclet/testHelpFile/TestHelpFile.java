/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug      7132631 8241693
 * @summary  Make sure that the help file is generated correctly.
 * @library  ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build    javadoc.tester.*
 * @run main TestHelpFile
 */

import javadoc.tester.JavadocTester;

public class TestHelpFile extends JavadocTester {
    /** A constant value to be documented. */
    public static final int ZERO = 0;

    public static void main(String... args) throws Exception {
        var tester = new TestHelpFile();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                "-sourcepath", testSrc,
                testSrc("TestHelpFile.java"));
        checkExit(Exit.OK);

        checkOutput("help-doc.html", true,
            """
                <a href="constant-values.html">Constant Field Values</a>""");

        // check a representative sample of the contents
        checkOrder("help-doc.html",
                """
                    <section class="help-section" id="package">
                    <h3>Package</h3>""",
                """
                    <ul class="help-section-list">
                    <li>Interfaces</li>
                    <li>Classes</li>
                    <li>Enum Classes</li>""",
                """
                    </section>
                    <section class="help-section" id="class">
                    <h3>Class or Interface</h3>""",
                """
                    <ul class="help-section-list">
                    <li>Class Inheritance Diagram</li>
                    <li>Direct Subclasses</li>
                    """);
    }
}
