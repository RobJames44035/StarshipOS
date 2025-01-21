/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8157611 8236949
 * @summary test DocTrees is working correctly relative to HTML access
 * @modules
 *      jdk.javadoc/jdk.javadoc.internal.api
 *      jdk.javadoc/jdk.javadoc.internal.tool
 *      jdk.compiler/com.sun.tools.javac.api
 *      jdk.compiler/com.sun.tools.javac.main
 * @library /tools/lib
 * @build toolbox.ToolBox toolbox.TestRunner
 * @run main TestDocTrees
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import toolbox.*;
import toolbox.Task.Expect;

import static toolbox.Task.OutputKind.*;

/**
 * This class is used to test DocTrees functionality relating to
 * package and overview HTML files.
 */
public class TestDocTrees extends TestRunner {
    final ToolBox tb;
    final File testFile;
    final File testSrc;
    final File overviewFile;

    TestDocTrees() throws IOException {
        super(System.err);
        tb = new ToolBox();
        testSrc = new File(System.getProperty("test.src"));
        testFile = new File(testSrc, "TestDocTrees.java");
        overviewFile = new File(testSrc, "overview.html");
    }

    protected void runTests() throws Exception {
        runTests(m -> new Object[]{Paths.get(m.getName())});
    }

    public static void main(String... args) throws Exception {
        new TestDocTrees().runTests();
    }

    @Test
    public void testOverviewWithRelease8(Path out) {
        execTask("-d", out.toString(),
                "--release", "8",
                "-Xdoclint:all",
                "-Xdoclint:-reference",
                "-sourcepath", testSrc.getAbsolutePath(),
                testFile.getAbsolutePath(),
                "-overview", overviewFile.getAbsolutePath());
    }

    @Test
    public void testOverviewWithoutRelease(Path out) throws Exception {
        execTask("-d", out.toString(),
                "-Xdoclint:all",
                "-Xdoclint:-reference",
                "-sourcepath", testSrc.getAbsolutePath(),
                testFile.getAbsolutePath(),
                "-overview", overviewFile.getAbsolutePath());
    }

    private Task.Result execTask(String... args) {
        JavadocTask et = new JavadocTask(tb, Task.Mode.CMDLINE);
        //args.forEach((a -> System.err.println("arg: " + a)));
        return et.options(args).run();
    }
}
