/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8331212
 * @summary Verify error recovery w.r.t. Flow
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main FlowRecovery
 */

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import toolbox.JavacTask;
import toolbox.Task.Expect;
import toolbox.Task.OutputKind;
import toolbox.TestRunner;
import toolbox.ToolBox;

public class FlowRecovery extends TestRunner {

    ToolBox tb;

    public FlowRecovery() {
        super(System.err);
        tb = new ToolBox();
    }

    public static void main(String[] args) throws Exception {
        FlowRecovery t = new FlowRecovery();
        t.runTests();
    }

    @Test //8331212
    public void testYieldErrors() throws Exception {
        String code = """
                      class Test {
                          public boolean test() {
                              return switch (0) {
                                  case 0 -> true;
                                  default -> {}
                              };
                          }
                      }
                      """;
        Path curPath = Path.of(".");
        List<String> actual = new JavacTask(tb)
                .options("-XDrawDiagnostics", "-XDdev")
                .sources(code)
                .outdir(curPath)
                .run(Expect.FAIL)
                .writeAll()
                .getOutputLines(OutputKind.DIRECT);

        List<String> expected = List.of(
                "Test.java:5:25: compiler.err.rule.completes.normally",
                "1 error"
        );

        if (!Objects.equals(actual, expected)) {
            error("Expected: " + expected + ", but got: " + actual);
        }
    }

}
