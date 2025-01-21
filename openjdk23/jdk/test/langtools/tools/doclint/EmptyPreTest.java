/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 8010317
 * @summary DocLint incorrectly reports some <pre> tags as empty
 * @modules jdk.javadoc/jdk.javadoc.internal.doclint
 * @build DocLintTester
 * @run main DocLintTester -Xmsgs:html EmptyPreTest.java
 */

public class EmptyPreTest {
    /** <pre> {@code xyzzy} </pre> */
    public void m1() { }

    /** <pre> {@docRoot} </pre> */
    public void m2() { }

    /** <pre> {@link java.lang.String} </pre> */
    public void m3() { }

    /** <pre> {@value} </pre> */
    public static final int v1 = 1;
}
