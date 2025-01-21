/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8025524 8031625 8081854 8175200 8186332 8182765
 * @summary Test for constructor name which should be a non-qualified name.
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main TestConstructors
 */

import javadoc.tester.JavadocTester;

public class TestConstructors extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestConstructors();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                "-sourcepath", testSrc,
                "pkg1");
        checkExit(Exit.OK);

        checkOutput("pkg1/Outer.html", true,
                """
                    <dt>See Also:</dt>
                    <dd>
                    <ul class="tag-list">
                    <li><a href="Outer.Inner.html#%3Cinit%3E()"><code>Inner()</code></a></li>
                    <li><a href="Outer.Inner.html#%3Cinit%3E(int)"><code>Inner(int)</code></a></li>
                    <li><a href="Outer.Inner.NestedInner.html#%3Cinit%3E()"><code>NestedInner()</code></a></li>
                    <li><a href="Outer.Inner.NestedInner.html#%3Cinit%3E(int)"><code>NestedInner(int)</code></a></li>
                    <li><a href="#%3Cinit%3E()"><code>Outer()</code></a></li>
                    <li><a href="#%3Cinit%3E(int)"><code>Outer(int)</code></a></li>
                    </ul>
                    </dd>""",
                """
                    Link: <a href="Outer.Inner.html#%3Cinit%3E()"><code>Inner()</code></a>, <a href=\
                    "#%3Cinit%3E(int)"><code>Outer(int)</code></a>, <a href="Outer.Inner.NestedInner\
                    .html#%3Cinit%3E(int)"><code>NestedInner(int)</code></a>""",
                """
                    <a href="#%3Cinit%3E()" class="member-name-link">Outer</a>()""",
                """
                    <section class="detail" id="&lt;init&gt;()">""",
                """
                    <a href="#%3Cinit%3E(int)" class="member-name-link">Outer</a><wbr>(int&nbsp;i)""",
                """
                    <section class="detail" id="&lt;init&gt;(int)">""");

        checkOutput("pkg1/Outer.Inner.html", true,
                """
                    <a href="#%3Cinit%3E()" class="member-name-link">Inner</a>()""",
                """
                    <section class="detail" id="&lt;init&gt;()">""",
                """
                    <a href="#%3Cinit%3E(int)" class="member-name-link">Inner</a><wbr>(int&nbsp;i)""",
                """
                    <section class="detail" id="&lt;init&gt;(int)">""");

        checkOutput("pkg1/Outer.Inner.NestedInner.html", true,
                """
                    <a href="#%3Cinit%3E()" class="member-name-link">NestedInner</a>()""",
                """
                    <section class="detail" id="&lt;init&gt;()">""",
                """
                    <a href="#%3Cinit%3E(int)" class="member-name-link">NestedInner</a><wbr>(int&nbsp;i)""",
                """
                    <section class="detail" id="&lt;init&gt;(int)">""");

        checkOutput("pkg1/Outer.Inner.html", false,
                "Outer.Inner()",
                "Outer.Inner(int)");

        checkOutput("pkg1/Outer.Inner.NestedInner.html", false,
                "Outer.Inner.NestedInner()",
                "Outer.Inner.NestedInner(int)");

        checkOutput("pkg1/Outer.html", false,
                """
                    <a href="Outer.Inner.html#Outer.Inner()"><code>Outer.Inner()</code></a>""",
                """
                    <a href="Outer.Inner.html#Outer.Inner(int)"><code>Outer.Inner(int)</code></a>""",
                """
                    <a href="Outer.Inner.NestedInner.html#Outer.Inner.NestedInner()"><code>Outer.Inner.NestedInner()</code></a>""",
                """
                    <a href="Outer.Inner.NestedInner.html#Outer.Inner.NestedInner(int)"><code>Outer.Inner.NestedInner(int)</code></a>""");
    }
}
