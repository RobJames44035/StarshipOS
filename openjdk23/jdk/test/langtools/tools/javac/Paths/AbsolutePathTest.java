/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8030046 8055500
 * @summary javac incorrectly handles absolute paths in manifest classpath
 * @author govereau
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @ignore 8055768 ToolBox does not close opened files
 * @build toolbox.ToolBox toolbox.JavacTask toolbox.JarTask
 * @run main AbsolutePathTest
 */

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import toolbox.JarTask;
import toolbox.JavacTask;
import toolbox.ToolBox;

public class AbsolutePathTest {
    public static void main(String... cmdline) throws Exception {
        ToolBox tb = new ToolBox();

        // compile test.Test
        new JavacTask(tb)
                .outdir(".") // this is needed to get the classfiles in test
                .sources("package test; public class Test{}")
                .run();

        // build test.jar containing test.Test
        // we need the jars in a directory different from the working
        // directory to trigger the bug.
        Files.createDirectory(Paths.get("jars"));
        new JarTask(tb, "jars/test.jar")
                .files("test/Test.class")
                .run();

        // build second jar in jars directory using
        // an absolute path reference to the first jar
        new JarTask(tb, "jars/test2.jar")
                .classpath(new File("jars/test.jar").getAbsolutePath())
                .run();

        // this should not fail
        new JavacTask(tb)
                .outdir(".")
                .classpath("jars/test2.jar")
                .sources("import test.Test; class Test2 {}")
                .run()
                .writeAll();
    }
}
