/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8305671
 * @summary Verify an extra semicolon is allowed after package decl with no imports
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.compiler/com.sun.tools.javac.util
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main ExtraPackageSemicolon
 */

import com.sun.tools.javac.Main;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import toolbox.TestRunner;
import toolbox.ToolBox;
import toolbox.JavacTask;
import toolbox.Task;

public class ExtraPackageSemicolon extends TestRunner {

    protected ToolBox tb;

    public ExtraPackageSemicolon() {
        super(System.err);
        tb = new ToolBox();
    }

    protected void runTests() throws Exception {
        runTests(m -> new Object[] { Paths.get(m.getName()) });
    }

    @Test
    public void test(Path base) throws Exception {
        testCompile(base, "package p;");
        testCompile(base, "package p;;");
        testCompile(base, "package p;;   ");
        testCompile(base, "package p\n;");
        testCompile(base, "package p\n;\n");
        testCompile(base, "package p\n;\n;\n");
        testCompile(base, "package p;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
        testCompile(base, "package p;; ;; ;; ;;; ;;; ;;; ;;");
        testCompile(base, "package p;;\n;;\n;;\n;;;\n;;;\n;;;\n;;");
        testCompile(base, "package p;\nimport java.util.Map;");
        testCompile(base, "package p;\nimport java.util.Map;\n;");
        testCompile(base, "package p;\nimport java.util.Map;\n;; ;;");
    }

    private void testCompile(Path base, String javaSource) throws Exception {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src, javaSource);
        Path classes = base.resolve("classes");
        tb.createDirectories(classes);
        new JavacTask(tb, Task.Mode.CMDLINE)
            .outdir(classes)
            .files(tb.findJavaFiles(src))
            .run(Task.Expect.SUCCESS);
    }

    public static void main(String... args) throws Exception {
        new ExtraPackageSemicolon().runTests();
    }
}
