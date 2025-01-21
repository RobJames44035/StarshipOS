/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8166367
 * @summary Missing ExceptionTable attribute in anonymous class constructors
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.jdeps/com.sun.tools.javap
 * @build toolbox.ToolBox toolbox.JavapTask
 * @run compile -g AnonymousCtorExceptionTest.java
 * @run main AnonymousCtorExceptionTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import toolbox.JavapTask;
import toolbox.Task;
import toolbox.ToolBox;

public class AnonymousCtorExceptionTest {

    AnonymousCtorExceptionTest() throws IOException {
    }

    public static void main(String args[]) throws Exception {

        new AnonymousCtorExceptionTest() {
        };

        ToolBox tb = new ToolBox();
        Path classPath = Paths.get(ToolBox.testClasses, "AnonymousCtorExceptionTest$1.class");
        String javapOut = new JavapTask(tb)
                .options("-v", "-p")
                .classes(classPath.toString())
                .run()
                .getOutput(Task.OutputKind.DIRECT);
        if (!javapOut.contains("AnonymousCtorExceptionTest$1() throws java.io.IOException;"))
            throw new AssertionError("Unexpected output " + javapOut);
        if (!javapOut.contains("Exceptions:"))
            throw new AssertionError("Unexpected output " + javapOut);
    }
}