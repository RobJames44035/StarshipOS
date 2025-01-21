/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug     4496223 4496270 4618686 4720974 4812240 6253614 6253604
 * @summary <DESC>
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main TestTagInheritance
 */

import javadoc.tester.JavadocTester;

public class TestTagInheritance extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestTagInheritance();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-Xdoclint:none",
                "-d", "out",
                "-sourcepath", testSrc,
                "pkg", "firstSentence", "firstSentence2");
        checkExit(Exit.OK);

        //Test bad inheritDoc tag warning.
        checkOutput(Output.OUT, true,
                "warning: @inheritDoc used but testBadInheritDocTag() "
                + "does not override or implement any method.");

        //Test valid usage of inheritDoc tag.
        for (int i = 1; i < 39; i++) {
            checkOutput("pkg/TestTagInheritance.html", true,
                    "Test " + i + " passes");
        }

        //First sentence test (6253614)
        checkOutput("firstSentence/B.html", true,
                """
                    <div class="block">First sentence.</div>""");

        //Another first sentence test (6253604)
        checkOutput("firstSentence2/C.html", true,
                """
                    <div class="block">First sentence.</div>""");
    }
}
