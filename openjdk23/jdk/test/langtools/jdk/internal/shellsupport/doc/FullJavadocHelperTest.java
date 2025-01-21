/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8189778
 * @summary Test JavadocHelper
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.jshell/jdk.internal.shellsupport.doc
 * @build toolbox.ToolBox toolbox.JarTask toolbox.JavacTask
 * @run testng/timeout=900/othervm -Xmx1024m FullJavadocHelperTest
 */

import java.io.IOException;

import org.testng.annotations.Test;

@Test
public class FullJavadocHelperTest {

    /*
     * Long-running test to retrieve doc comments for enclosed elements of all JDK classes.
     */
    public void testAllDocs() throws IOException {
        new JavadocHelperTest().retrieveDocComments(Boolean.TRUE::booleanValue);
    }
}
