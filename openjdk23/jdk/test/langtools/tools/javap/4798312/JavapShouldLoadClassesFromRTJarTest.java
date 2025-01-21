/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 4798312
 * @summary In Windows, javap doesn't load classes from the runtime image
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.jdeps/com.sun.tools.javap
 * @build toolbox.ToolBox toolbox.JavapTask
 * @run main JavapShouldLoadClassesFromRTJarTest
 */

import toolbox.JavapTask;
import toolbox.Task;
import toolbox.ToolBox;

public class JavapShouldLoadClassesFromRTJarTest {

    public static void main(String[] args) throws Exception {
        ToolBox tb = new ToolBox();
        String out = new JavapTask(tb)
                .options("-v")
                .classes("java.lang.String")
                .run()
                .getOutput(Task.OutputKind.DIRECT);

        if (out.isEmpty())
            throw new AssertionError("javap generated no output");
    }

}
