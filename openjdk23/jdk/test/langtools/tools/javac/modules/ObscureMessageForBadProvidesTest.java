/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8152062
 * @summary obscure error message for bad 'provides'
 * @library /tools/lib
 * @modules
 *      jdk.compiler/com.sun.tools.javac.api
 *      jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox toolbox.JavacTask ModuleTestBase
 * @run main ObscureMessageForBadProvidesTest
 */

import java.nio.file.Path;

import toolbox.JavacTask;
import toolbox.Task;
import toolbox.ToolBox;

public class ObscureMessageForBadProvidesTest extends ModuleTestBase {
    public static void main(String... args) throws Exception {
        new ObscureMessageForBadProvidesTest().runTests();
    }

    @Test
    public void theTest(Path base) throws Exception {
        Path mod = base.resolve("mod");
        tb.writeJavaFiles(mod, "module mod { provides java.lang.String with java.io.File; }");
        Path classes = base.resolve("classes");
        tb.createDirectories(classes);
        String log = new JavacTask(tb)
                .options("-XDrawDiagnostics")
                .outdir(classes)
                .files(findJavaFiles(mod))
                .run(Task.Expect.FAIL)
                .writeAll()
                .getOutput(Task.OutputKind.DIRECT);

        if (!log.startsWith("module-info.java:1:52: compiler.err.service.implementation.must.be.subtype.of.service.interface"))
            throw new Exception("expected output not found");
    }
}
