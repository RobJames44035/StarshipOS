/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @bug 8287885 8296656 7016187
 * @summary Verify proper function of the "output-file-clash" lint flag
 * @requires os.family == "mac"
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.compiler/com.sun.tools.javac.util
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main OutputFileClashTest
*/

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

import toolbox.TestRunner;
import toolbox.ToolBox;
import toolbox.JavacTask;
import toolbox.Task;

public class OutputFileClashTest extends TestRunner {

    protected ToolBox tb;

    public OutputFileClashTest() {
        super(System.err);
        tb = new ToolBox();
    }

    protected void runTests() throws Exception {
        runTests(m -> new Object[] { Paths.get(m.getName()) });
    }

    Path[] findJavaFiles(Path... paths) throws IOException {
        return tb.findJavaFiles(paths);
    }

// Note: in these tests, it's indeterminate which output file gets written first.
// So we compare the log output to a regex that matches the error either way.

    @Test
    public void testBug8287885(Path base) throws Exception {
        testClash(base,
                """
                public class Test {
                    void method1() {
                        enum ABC { A, B, C; };  // becomes "Test$1ABC.class"
                    }
                    void method2() {
                        enum Abc { A, B, C; };  // becomes "Test$1Abc.class"
                    }
                }
                """,
            "- compiler.warn.output.file.clash: .*Test\\$1(ABC|Abc)\\.class");
    }

    @Test
    public void testBug8296656(Path base) throws Exception {
        testClash(base,
                """
                public class Test {
                    @interface Annotation {
                        interface foo { }
                        @interface Foo { }
                    }
                }
                """,
            "- compiler.warn.output.file.clash: .*Test\\$Annotation\\$(foo|Foo)\\.class");
    }

    @Test
    public void testCombiningAcuteAccent(Path base) throws Exception {
        testClash(base,
                """
                public class Test {
                    interface Cafe\u0301 {      // macos normalizes "e" + U0301 -> U00e9
                    }
                    interface Caf\u00e9 {
                    }
                }
                """,
            "- compiler.warn.output.file.clash: .*Test\\$Caf.*\\.class");
    }

    private void testClash(Path base, String javaSource, String regex) throws Exception {

        // Compile source
        Path src = base.resolve("src");
        tb.writeJavaFiles(src, javaSource);
        Path classes = base.resolve("classes");
        tb.createDirectories(classes);
        Path headers = base.resolve("headers");
        tb.createDirectories(headers);
        List<String> log = new JavacTask(tb, Task.Mode.CMDLINE)
                .options("-XDrawDiagnostics", "-Werror", "-Xlint:output-file-clash")
                .outdir(classes)
                .headerdir(headers)
                .files(findJavaFiles(src))
                .run(Task.Expect.FAIL)
                .writeAll()
                .getOutputLines(Task.OutputKind.DIRECT);

        // Find expected error line
        Pattern pattern = Pattern.compile(regex);
        if (!log.stream().anyMatch(line -> pattern.matcher(line).matches()))
            throw new Exception("expected error not found: \"" + regex + "\"");
    }

    public static void main(String... args) throws Exception {
        new OutputFileClashTest().runTests();
    }
}
