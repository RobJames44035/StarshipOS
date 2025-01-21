/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8007566
 * @summary DocLint too aggressive with not allowed here: <p>
 * @modules jdk.javadoc/jdk.javadoc.internal.doclint
 * @build DocLintTester
 * @run main DocLintTester -Xmsgs ParaTagTest.java
 */

/**
 * First line.
 * <p> Para c1.</p>
 * <p> Para c2.
 * <p> Para c3.</p>
 */
public class ParaTagTest {
    /**
     * m1 <code>code </code>.
     * <p> Para m1.
     * <p> Para m2.
     */
    public void m() {}

    /**
     * m2.
     * <p> Para z1.
     * <p> Para z2.
     * <pre>
     *    Preformat 1.
     * </pre>
     */
    public void z() {}

    /** . */
    ParaTagTest() { }
}
