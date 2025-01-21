/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8130880
 * @library lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.code
 *          jdk.compiler/com.sun.tools.javac.parser
 *          jdk.compiler/com.sun.tools.javac.tree
 *          jdk.compiler/com.sun.tools.javac.util
 *          jdk.javadoc/jdk.javadoc.internal.tool
 * @run main SampleApiTest
 */
import java.io.File;

import jdk.javadoc.internal.tool.Main;
import sampleapi.SampleApiDefaultRunner;

public class SampleApiTest {

    public static void main(String... args) throws Exception {

        // generate
        SampleApiDefaultRunner.execute(
            new String[] {
                "-o=out/src",
                "-r=" + System.getProperty("test.src") + "/res"
            });

        // html5 / unnamed modules
        System.err.println(">> HTML5, unnamed modules");
        int res = Main.execute(
            new String[] {
                "-d", "out/doc.html5.unnamed",
                "-verbose",
                "-private",
                "-use",
                "-splitindex",
                "-linksource",
                "-html5",
                "-javafx",
                "-windowtitle", "SampleAPI",
                "-overview", "overview.html",
                "-sourcepath", "out/src" + File.pathSeparator + "out/src/sat.sampleapi",
                "sampleapi.simple",
                "sampleapi.simple.sub",
                "sampleapi.tiny",
                "sampleapi.tiny.sub",
                "sampleapi.fx"
            });

        if (res > 0)
            throw new Exception("exit status is non-zero: " + res + " for HTML5.");
    }
}
