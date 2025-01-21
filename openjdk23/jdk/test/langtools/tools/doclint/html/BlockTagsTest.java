/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8006251
 * @summary test block tags
 * @library ..
 * @modules jdk.javadoc/jdk.javadoc.internal.doclint
 * @build DocLintTester
 * @run main DocLintTester -Xmsgs:-accessibility BlockTagsTest.java
 */

/** */
public class BlockTagsTest {
    /**
     *  <blockquote> abc </blockquote>
     *  <center> abc </center>
     *  <div> abc </div>
     *  <dl> <dt> abc <dd> def </dl>
     *  <div> abc </div>
     *  <h1> abc </h1>
     *  <h2> abc </h2>
     *  <h3> abc </h3>
     *  <h4> abc </h4>
     *  <h5> abc </h5>
     *  <h6> abc </h6>
     *  <hr>
     *  <menu> <li> abc </menu>
     *  <noscript> </noscript>
     *  <ol> <li> abc </ol>
     *  <p> abc </p>
     *  <pre> abc </pre>
     *  <table summary="abc"> <tr> <td> </table>
     *  <ul> <li> abc </ul>
     */
    public void supportedTags() { }
}
