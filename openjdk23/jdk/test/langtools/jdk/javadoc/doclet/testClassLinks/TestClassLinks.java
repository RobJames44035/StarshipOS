/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8163800 8175200 8186332 8182765 8345908
 * @summary The fix for JDK-8072052 shows up other minor incorrect use of styles
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @build TestClassLinks
 * @run main TestClassLinks
 */

import javadoc.tester.JavadocTester;

public class TestClassLinks extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestClassLinks();
        tester.runTests();
    }

    @Test
    public void test() {

        javadoc("-d", "out",
                "-Xdoclint:none",
                "--no-platform-links",
                "-sourcepath", testSrc,
                "-package",
                "p");
        checkExit(Exit.OK);

        checkOutput("p/C1.html", true,
                """
                    <code><a href="C2.html" title="class in p">C2</a></code>""",
                """
                    <code><a href="#%3Cinit%3E()" class="member-name-link">C1</a>()</code>""");

        checkOutput("p/C2.html", true,
                """
                    <code><a href="C3.html" title="class in p">C3</a></code>""",
                """
                    <code><a href="#%3Cinit%3E()" class="member-name-link">C2</a>()</code>""");

        checkOutput("p/C3.html", true,
                """
                    <code><a href="I1.html" title="interface in p">I1</a>, <a href="I12\
                    .html" title="interface in p">I12</a>, <a href="I2.html" title="int\
                    erface in p">I2</a>, <a href="IT1.html" title="interface in p">IT1<\
                    /a>&lt;T&gt;, <a href="IT2.html" title="interface in p">IT2</a>&lt;\
                    java.lang.String&gt;</code>""",
                """
                    <code><a href="#%3Cinit%3E()" class="member-name-link">C3</a>()</code>""");

        checkOutput("p/I1.html", true,
                """
                    <code><a href="C3.html" title="class in p">C3</a></code>""",
                """
                    <code><a href="I12.html" title="interface in p">I12</a></code>""");

        checkOutput("p/I2.html", true,
                """
                    <code><a href="C3.html" title="class in p">C3</a></code>""",
                """
                    <code><a href="I12.html" title="interface in p">I12</a></code>""");

        checkOutput("p/I12.html", true,
                """
                    <code><a href="C3.html" title="class in p">C3</a></code>""",
                """
                    <code><a href="I1.html" title="interface in p">I1</a>, <a href="I2.\
                    html" title="interface in p">I2</a></code>""");

        checkOutput("p/IT1.html", true,
                """
                    <code><a href="C3.html" title="class in p">C3</a></code>""");

        checkOutput("p/IT2.html", true,
                """
                    code><a href="C3.html" title="class in p">C3</a></code>""");
    }
}
