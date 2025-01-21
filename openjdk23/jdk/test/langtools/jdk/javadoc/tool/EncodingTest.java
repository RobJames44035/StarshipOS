/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8188649
 * @summary ensure javadoc -encoding is not ignored
 * @modules jdk.compiler/com.sun.tools.javac.api
 * @modules jdk.compiler/com.sun.tools.javac.main
 * @modules jdk.javadoc/jdk.javadoc.internal.api
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @library /tools/lib
 * @build toolbox.JavacTask toolbox.JavadocTask toolbox.TestRunner toolbox.ToolBox
 * @run main EncodingTest
 */

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import toolbox.JavadocTask;
import toolbox.Task;
import toolbox.TestRunner;
import toolbox.ToolBox;

public class EncodingTest extends TestRunner {
    public static void main(String... args) throws Exception {
        EncodingTest t = new EncodingTest();
        t.runTests();
    }

    private final ToolBox tb = new ToolBox();
    private final Path src = Paths.get("src");
    private final Path api = Paths.get("api");

    EncodingTest() throws Exception {
        super(System.err);
        init();
    }

    void init() throws IOException {
        Files.createDirectories(src);
        Files.write(src.resolve("C.java"),
                "/** \u03b1\u03b2\u03b3 */ public class C { }".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void testEncoding() {
        Task.Result result = new JavadocTask(tb, Task.Mode.EXEC)
                .outdir(api)
                .options("-J-Dfile.encoding=ASCII",
                        "-encoding", "UTF-8",
                        "-docencoding", "UTF-8")
                .files(src.resolve("C.java"))
                .run(Task.Expect.SUCCESS)
                .writeAll();
    }
}

