/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test 8160156
 * @summary javac is generating let expressions unnecessarily
 * @library /tools/lib
 * @modules
 *      jdk.compiler/com.sun.tools.javac.api
 *      jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main LetExpressionsAreUnnecessarilyGeneratedTest
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import toolbox.JavacTask;
import toolbox.Task;
import toolbox.TestRunner;
import toolbox.ToolBox;

public class LetExpressionsAreUnnecessarilyGeneratedTest extends TestRunner {
    ToolBox tb;

    public static void main(String... args) throws Exception {
        new LetExpressionsAreUnnecessarilyGeneratedTest().runTests();
    }

    public LetExpressionsAreUnnecessarilyGeneratedTest() {
        super(System.err);
        tb = new ToolBox();
    }

    protected void runTests() throws Exception {
        runTests(m -> new Object[] { Paths.get(m.getName()) });
    }

    @Test
    public void testDontGenerateLetExpr(Path testBase) throws Exception {
        Path src = testBase.resolve("src");
        tb.writeJavaFiles(src,
                "package base;\n" +
                "public abstract class Base {\n" +
                "    protected int i = 1;\n" +
                "}",

                "package sub;\n" +
                "import base.Base;\n" +
                "public class Sub extends Base {\n" +
                "    private int i = 4;\n" +
                "    void m() {\n" +
                "        new Runnable() {\n" +
                "            public void run() {\n" +
                "                Sub.super.i += 10;\n" +
                "            }\n" +
                "        };\n" +
                "    }\n" +
                "}");

        Path out = testBase.resolve("out");
        Files.createDirectories(out);
        Path base = src.resolve("base");
        Path sub = src.resolve("sub");

        new JavacTask(tb)
            .outdir(out)
            .files(tb.findJavaFiles(base))
            .run(Task.Expect.SUCCESS);

        new JavacTask(tb)
            .classpath(out)
            .outdir(out)
            .files(tb.findJavaFiles(sub))
            .run(Task.Expect.SUCCESS);
    }
}
