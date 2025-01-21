/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8210009 8321739
 * @summary Source Launcher classloader should support getResource and getResourceAsStream
 * @modules jdk.compiler
 * @library /tools/lib
 * @build toolbox.JavaTask toolbox.ToolBox
 * @run main GetResourceTest
 */

import java.nio.file.Path;
import java.nio.file.Paths;

import toolbox.JavaTask;
import toolbox.Task;
import toolbox.ToolBox;

/*
 * The body of this test is in ${test.src}/src/p/q/CLTest.java,
 * which is executed in source-launcher mode,
 * in order to test the classloader used to launch such programs.
 */
public class GetResourceTest {
    public static void main(String... args) throws Exception {
        GetResourceTest t = new GetResourceTest();
        t.run();
    }

    void run() throws Exception {
        ToolBox tb = new ToolBox();
        Path file = Paths.get(tb.testSrc).resolve("src/p/q").resolve("CLTest.java");
        new JavaTask(tb)
            .className(file.toString()) // implies source file mode
            .run(Task.Expect.SUCCESS)
            .writeAll();
    }
}
