/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8160928
 * @summary javac incorrectly copies over interior type annotations to bridge method
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.jdeps/com.sun.tools.javap
 * @build toolbox.ToolBox toolbox.JavapTask
 * @run compile -g BridgeShouldHaveNoInteriorAnnotationsTest.java
 * @run main BridgeShouldHaveNoInteriorAnnotationsTest
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import toolbox.JavapTask;
import toolbox.Task;
import toolbox.ToolBox;

class Pair_8160928<T1, T2> {
}

public class BridgeShouldHaveNoInteriorAnnotationsTest
                   implements java.util.Iterator<Pair_8160928<Object, Object>> {

    @Override
    public boolean hasNext() {
        throw new RuntimeException();
    }

    @Override
    public Pair_8160928<@NonNull Object, Object> next() {
        Comparable<@NonNull Object> cble1 = (Comparable<@NonNull Object>) null;
        return null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }


    @Target(ElementType.TYPE_USE)
    public @interface NonNull {
    };


    // Expected output can't be directly encoded into NestedLambdasCastedTest !!!
    static class OutputExpectedOnceHolder {
        public String[] outputs = {
            "0: #120(): CAST, offset=1, type_index=0, location=[TYPE_ARGUMENT(0)]",
            "1: #120(): LOCAL_VARIABLE, {start_pc=5, length=2, index=1}, location=[TYPE_ARGUMENT(0)]",
        };
    }

    static class OutputExpectedTwiceHolder {
        public String[] outputs = {
            "0: #120(): METHOD_RETURN, location=[TYPE_ARGUMENT(0)]",
        };
    }

    public static strictfp void main(String args[]) throws Exception {
        ToolBox tb = new ToolBox();
        Path classPath = Paths.get(ToolBox.testClasses, "BridgeShouldHaveNoInteriorAnnotationsTest.class");
        String javapOut = new JavapTask(tb)
                .options("-v", "-p")
                .classes(classPath.toString())
                .run()
                .getOutput(Task.OutputKind.DIRECT);

        OutputExpectedOnceHolder holder = new OutputExpectedOnceHolder();
        for (String s : holder.outputs) {
            String newOutput = javapOut.replace(s, "");
            if (((javapOut.length() - newOutput.length()) / s.length()) != 1)
                throw new AssertionError("Interior annotations carried over to bridge ?");
        }
        OutputExpectedTwiceHolder holder2 = new OutputExpectedTwiceHolder();
        for (String s : holder2.outputs) {
            String newOutput = javapOut.replace(s, "");
            if (((javapOut.length() - newOutput.length()) / s.length()) != 2)
                throw new AssertionError("Exterior annotations not properly carried over to bridge");
        }
    }
}
