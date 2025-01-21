/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8267361 8325440
 * @summary Verify meaniningfull errors for broken octal literals.
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main OctalNumberTest
 */

import java.util.Arrays;
import java.util.List;

import toolbox.JavacTask;
import toolbox.ToolBox;
import toolbox.TestRunner;
import toolbox.Task;

public class OctalNumberTest extends TestRunner {
    ToolBox tb;

    OctalNumberTest() {
        super(System.err);
        tb = new ToolBox();
    }

    public static void main(String[] args) throws Exception {
        var t = new OctalNumberTest();
        t.runTests();
    }

    @Test
    public void testOctalNumber() throws Exception {
        String code = """
                class Digit {
                    int a = 023; // normal
                    int b = 089;
                    int c = 02389;
                    int d = 028a;
                    int e = 02a8;
                    int f = 0b;
                    int g = 0b2;
                    int h = 0b12;
                }""";
        List<String> output = new JavacTask(tb)
                .sources(code)
                .options("-XDrawDiagnostics")
                .run(Task.Expect.FAIL)
                .writeAll()
                .getOutputLines(Task.OutputKind.DIRECT);
        List<String> expected = Arrays.asList(
                "Digit.java:3:14: compiler.err.illegal.digit.in.octal.literal",
                "Digit.java:4:16: compiler.err.illegal.digit.in.octal.literal",
                "Digit.java:5:15: compiler.err.illegal.digit.in.octal.literal",
                "Digit.java:5:16: compiler.err.expected: ';'",
                "Digit.java:5:17: compiler.err.expected: token.identifier",
                "Digit.java:6:15: compiler.err.expected: ';'",
                "Digit.java:6:17: compiler.err.expected: token.identifier",
                "Digit.java:7:13: compiler.err.invalid.binary.number",
                "Digit.java:8:13: compiler.err.illegal.digit.in.binary.literal",
                "Digit.java:9:14: compiler.err.illegal.digit.in.binary.literal",
                "10 errors");
        tb.checkEqual(expected, output);
    }
}
