/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug      4665566 4855876 7025314 8012375 8015997 8016328 8024756 8148985 8151921 8151743 8196202 8223378
 * @summary  Verify that the output has the right javascript.
 * @library  ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build    javadoc.tester.*
 * @run main TestJavascript
 */

import javadoc.tester.JavadocTester;

public class TestJavascript extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestJavascript();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                "-sourcepath", testSrc,
                "pkg", testSrc("TestJavascript.java"));
        checkExit(Exit.OK);

        checkOutput("pkg/C.html", false,
                """
                    <script type="text/javascript"><!--
                    $('.navPadding').css('padding-top', $('.fixedNav').css("height"));
                    //-->
                    </script>""");

        checkOutput("index.html", false,
                """
                    <script type="text/javascript"><!--
                    $('.navPadding').css('padding-top', $('.fixedNav').css("height"));
                    //-->
                    """);

        checkOutput("script-files/script.js", false,
                """
                    $(window).resize(function() {
                            $('.navPadding').css('padding-top', $('.fixedNav').css("height"));
                        });""");
    }
}
