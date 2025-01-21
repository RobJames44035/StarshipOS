/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6604599
 * @summary ToolProvider should be less compiler-specific
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox toolbox.JavaTask
 * @run main ToolProviderTest2
 */

import javax.tools.ToolProvider;
import java.util.List;

import toolbox.JavaTask;
import toolbox.Task;
import toolbox.ToolBox;

// control for ToolProviderTest1 -- verify that using ToolProvider to
// access the compiler does trigger loading com.sun.tools.javac.*
public class ToolProviderTest2 {
    public static void main(String... args) throws Exception {
        if (args.length > 0) {
            System.err.println(ToolProvider.getSystemJavaCompiler());
            return;
        }

        new ToolProviderTest2().run();
    }

    void run() throws Exception {
        ToolBox tb = new ToolBox();
        String classpath = System.getProperty("java.class.path");

        List<String> lines = new JavaTask(tb)
                .vmOptions("-verbose:class")
                .classpath(classpath)
                .className(getClass().getName())
                .classArgs("javax.tools.ToolProvider")
                .run()
                .getOutputLines(Task.OutputKind.STDOUT);

        boolean found = false;
        for (String line : lines) {
            System.err.println(line);
            if (line.contains("com.sun.tools.javac."))
                found = true;
        }

        if (!found)
            error("expected class name not found");

        if (errors > 0)
            throw new Exception(errors + " errors occurred");
    }

    void error(String msg) {
        System.err.println(msg);
        errors++;
    }

    int errors;
}
