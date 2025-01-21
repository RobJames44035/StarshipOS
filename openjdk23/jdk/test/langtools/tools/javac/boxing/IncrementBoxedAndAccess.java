/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @bug 8143388
 * @summary Verify that boxed postfix operator works properly when referring to super class' field.
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.jdeps/com.sun.tools.javap
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main IncrementBoxedAndAccess
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import toolbox.JavacTask;
import toolbox.ToolBox;

public class IncrementBoxedAndAccess {
    public static void main(String... args) throws IOException {
        new IncrementBoxedAndAccess().run();
    }

    void run() throws IOException {
        ToolBox tb = new ToolBox();

        Path expected = Paths.get("expected");
        Files.createDirectories(expected);
        tb.cleanDirectory(expected);
        new JavacTask(tb)
          .sources("package p1;" +
                   "public class B {" +
                   "    protected Integer i;" +
                   "}",
                   "package p2;" +
                   "public class S extends p1.B {" +
                   "    public void i() { i++; }" +
                   "    private class I {" +
                   "        void i() { i++; }" +
                   "        private class II {" +
                   "            void i() { i++; }" +
                   "        }" +
                   "    }" +
                   "}")
          .outdir(expected)
          .run();

        Path actual = Paths.get("actual");
        Files.createDirectories(actual);
        tb.cleanDirectory(actual);
        new JavacTask(tb)
          .sources("package p1;" +
                   "public class B {" +
                   "    protected Integer i;" +
                   "}",
                   "package p2;" +
                   "public class S extends p1.B {" +
                   "    public void i() { super.i++; }" +
                   "    private class I {" +
                   "        void i() { S.super.i++; }" +
                   "        private class II {" +
                   "            void i() { S.super.i++; }" +
                   "        }" +
                   "    }" +
                   "}")
          .outdir(actual)
          .run();
    }
}
