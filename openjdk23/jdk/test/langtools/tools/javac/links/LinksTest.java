/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 4266026
 * @summary javac no longer follows symlinks
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main LinksTest
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import toolbox.JavacTask;
import toolbox.ToolBox;

// Original test: test/tools/javac/links/links.sh
public class LinksTest {

    private static final String BSrc =
        "package a;\n" +
        "\n" +
        "public class B {}";

    private static final String TSrc =
        "class T extends a.B {}";

    public static void main(String... args) throws Exception {
        ToolBox tb = new ToolBox();
        tb.writeFile("tmp/B.java", BSrc);

        // Try to set up a symbolic link for the test.
        try {
            Files.createSymbolicLink(Paths.get("a"), Paths.get("tmp"));
            System.err.println("Created symbolic link");
        } catch (UnsupportedOperationException | IOException e) {
            System.err.println("Problem creating symbolic link: " + e);
            System.err.println("Test cannot continue; test passed by default");
            return;
        }

        // If symbolic link was successfully created,
        // try a compilation that will use it.
        new JavacTask(tb)
                .sourcepath(".")
                .outdir(".")
                .sources(TSrc)
                .run()
                .writeAll();
    }

}
