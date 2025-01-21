/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 4509051 4785453
 * @summary javac <AT>sourcefiles should catch Exception, when sourcefiles
 * doesn't exist.
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main MissingIncludeTest
 */

import toolbox.JavacTask;
import toolbox.Task;
import toolbox.ToolBox;

// Original test: test/tools/javac/MissingInclude.sh
public class MissingIncludeTest {

    private static final String MissingIncludeFile = "MissingInclude.java";
    private static final String MissingIncludeSrc = "class MissingInclude {}";

    public static void main(String[] args) throws Exception {
        ToolBox tb = new ToolBox();

        tb.writeFile(MissingIncludeFile, MissingIncludeSrc);

        new JavacTask(tb, Task.Mode.CMDLINE)
                .options("@/nonexistent_file")
                .files(MissingIncludeFile)
                .run(Task.Expect.FAIL);
    }

}
