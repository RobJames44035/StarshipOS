/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8144168 8148432
 * @summary No type annotations generated for nested lambdas
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.jdeps/com.sun.tools.javap
 * @build toolbox.ToolBox toolbox.JavapTask
 * @run compile -source 16 -target 16 -g NestedLambdasCastedTest.java
 * @run main NestedLambdasCastedTest
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import toolbox.JavapTask;
import toolbox.Task;
import toolbox.ToolBox;

public class NestedLambdasCastedTest {

    // Expected output can't be directly encoded into NestedLambdasCastedTest !!!
    static class ExpectedOutputHolder {
        public String[] outputs = {
                      "public static strictfp void main(java.lang.String[])",
                      "private static strictfp void lambda$main$3();",
                      "private static strictfp void lambda$main$2();",
                      "private static strictfp void lambda$main$1();",
                      "private static strictfp void lambda$main$0();",
                      "0: #111(#112=s#113): CAST, offset=5, type_index=0",
                      "0: #111(#112=s#119): CAST, offset=5, type_index=0",
                      "0: #111(#112=s#122): CAST, offset=5, type_index=0",
                      "0: #111(#112=s#125): CAST, offset=5, type_index=0"
        };
    }

    @Target(ElementType.TYPE_USE)
    public @interface TA {
        String value() default "";
    };

    public static strictfp void main(String args[]) throws Exception {
        Runnable one = (@TA("1") Runnable) () -> {
            Runnable two = (@TA("2") Runnable) () -> {
                Runnable three = (@TA("3") Runnable) () -> {
                    Runnable four = (@TA("4") Runnable) () -> {
                    };
                };
            };
        };
        ToolBox tb = new ToolBox();
        Path classPath = Paths.get(ToolBox.testClasses, "NestedLambdasCastedTest.class");
        String javapOut = new JavapTask(tb)
                .options("-v", "-p")
                .classes(classPath.toString())
                .run()
                .getOutput(Task.OutputKind.DIRECT);
        ExpectedOutputHolder holder = new ExpectedOutputHolder();
        for (String s : holder.outputs) {
            if (!javapOut.contains(s))
                throw new AssertionError("Expected type annotation on LOCAL_VARIABLE missing");
        }
    }
}
