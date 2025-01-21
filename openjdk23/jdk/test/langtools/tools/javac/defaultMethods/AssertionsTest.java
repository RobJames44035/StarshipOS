/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8025141
 * @summary Interfaces must not contain non-public fields, ensure $assertionsDisabled
 *          is not generated into an interface
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox toolbox.JavaTask Assertions AssertionsTest
 * @run main AssertionsTest -da
 * @run main AssertionsTest -ea:test.Assertions Inner
 * @run main AssertionsTest -ea:test.Outer Outer
 * @run main AssertionsTest -ea:test.Another Another.Inner
 * @run main AssertionsTest -ea:test... Inner Outer Another.Inner
 */

import java.util.Arrays;

import toolbox.JavaTask;
import toolbox.Task;
import toolbox.ToolBox;

public class AssertionsTest {

    public static void main(String... args) throws Exception {
        String testClasses = System.getProperty("test.classes");
        ToolBox tb = new ToolBox();
        new JavaTask(tb).classpath(testClasses)
                         .vmOptions(args[0])
                         .className("test.Assertions")
                         .classArgs(Arrays.copyOfRange(args, 1, args.length))
                         .includeStandardOptions(false)
                         .run(Task.Expect.SUCCESS)
                         .writeAll();
    }

}
