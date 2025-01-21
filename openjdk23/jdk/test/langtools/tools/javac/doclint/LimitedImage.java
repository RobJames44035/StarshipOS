/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/**
 * @test
 * @bug 8253996
 * @summary Verify doclint behavior when doclint not available
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @run main/othervm --limit-modules jdk.compiler,jdk.zipfs LimitedImage
 */

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import toolbox.JavacTask;
import toolbox.Task.Expect;
import toolbox.Task.Mode;
import toolbox.Task.OutputKind;
import toolbox.ToolBox;

public class LimitedImage {
    public static void main(String... args) throws IOException {
        ToolBox tb = new ToolBox();

        //showing help should be OK
        new JavacTask(tb, Mode.CMDLINE)
                .options("--help")
                .run().writeAll();

        Path testSource = Path.of("Test.java");
        tb.writeFile(testSource, "class Test {}");

        List<String> actualOutput;
        List<String> expectedOutput = List.of(
                "- compiler.warn.doclint.not.available",
                "1 warning"
        );

        //check proper diagnostics when doclint provider not present:
        System.err.println("Test -Xdoclint when doclint not available");
        actualOutput = new JavacTask(tb, Mode.CMDLINE)
                .options("-XDrawDiagnostics", "-Xdoclint")
                .files(testSource)
                .outdir(".")
                .run(Expect.SUCCESS)
                .writeAll()
                .getOutputLines(OutputKind.DIRECT);

        tb.checkEqual(expectedOutput, actualOutput);
    }

}
