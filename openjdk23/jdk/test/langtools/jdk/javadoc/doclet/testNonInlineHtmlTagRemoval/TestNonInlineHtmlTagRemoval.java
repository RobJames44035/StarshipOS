/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug      8048628 8174715 8182765
 * @summary  Verify html inline tags are removed correctly in the first sentence.
 * @library  ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build    javadoc.tester.*
 * @run main TestNonInlineHtmlTagRemoval
 */

import javadoc.tester.JavadocTester;

public class TestNonInlineHtmlTagRemoval extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestNonInlineHtmlTagRemoval();
        tester.runTests();
    }

    @Test
    public void testPositive() {
        javadoc("-d", "out1",
                "-sourcepath", testSrc,
                testSrc("C.java"));
        checkExit(Exit.ERROR);

        checkOutput(Output.OUT, true,
                "attribute not supported in HTML5: compact",
                "attribute not supported in HTML5: type");

        checkOutput("C.html", true,
                """
                    <div class="block">case1   end of sentence.</div>""",
                """
                    <div class="block">case2   end of sentence.</div>""",
                """
                    <div class="block">case3   end of sentence.</div>""",
                """
                    <div class="block">case4   end of sentence.</div>""",
                """
                    <div class="block">case5   end of sentence.</div>""",
                """
                    <div class="block">case6   end of sentence.</div>""",
                """
                    <div class="block">case7   end of sentence.</div>""",
                """
                    <div class="block">case8   end of sentence.</div>""",
                """
                    <div class="block">case9   end of sentence.</div>""",
                """
                    <div class="block">caseA   end of sentence.</div>""",
                """
                    <div class="block">caseB A block quote example:</div>""");
    }

    @Test
    public void testNegative() {
        javadoc("-d", "out2",
                "-sourcepath", testSrc,
                testSrc("Negative.java"));
        checkExit(Exit.ERROR);

        checkOutput("Negative.html", true,
                """
                    <div class="block">case1: A hanging &lt;  : xx<span class="invalid-tag">invalid input: '&lt;'</span></div>""");
    }
}
