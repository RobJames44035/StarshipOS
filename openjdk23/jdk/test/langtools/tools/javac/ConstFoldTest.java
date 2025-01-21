/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8025505
 * @summary Constant folding deficiency
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.jdeps/com.sun.tools.javap
 * @build toolbox.ToolBox toolbox.JavapTask
 * @run main ConstFoldTest
 */

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import toolbox.JavapTask;
import toolbox.Task;
import toolbox.ToolBox;

public class ConstFoldTest {
    public static void main(String... args) throws Exception {
        new ConstFoldTest().run();
    }

    // This is the test case. This class should end up
    // as straight-line code with no conditionals
    class CFTest {
        void m() {
            int x;
            if (1 != 2)       x=1; else x=0;
            if (1 == 2)       x=1; else x=0;
            if ("" != null)   x=1; else x=0;
            if ("" == null)   x=1; else x=0;
            if (null == null) x=1; else x=0;
            if (null != null) x=1; else x=0;

            x = 1 != 2        ? 1 : 0;
            x = 1 == 2        ? 1 : 0;
            x = "" != null    ? 1 : 0;
            x = "" == null    ? 1 : 0;
            x = null == null  ? 1 : 0;
            x = null != null  ? 1 : 0;

            boolean b;
            b = 1 != 2         && true;
            b = 1 == 2         || true;
            b = ("" != null)   && true;
            b = ("" == null)   || true;
            b = (null == null) && true;
            b = (null != null) || true;
        }
    }

    // All of the conditionals above should be eliminated.
    // these if* bytecodes should not be seen
    final String regex = "\\sif(?:null|nonnull|eq|ne){1}\\s";

    void run() throws Exception {
        ToolBox tb = new ToolBox();

        URL url = ConstFoldTest.class.getResource("ConstFoldTest$CFTest.class");
        Path file = Paths.get(url.toURI());
        List<String> result = new JavapTask(tb)
                .options("-c")
                .classes(file.toString())
                .run()
                .write(Task.OutputKind.DIRECT)
                .getOutputLines(Task.OutputKind.DIRECT);

        List<String> bad_codes = tb.grep(regex, result);
        if (!bad_codes.isEmpty()) {
            for (String code : bad_codes)
                System.out.println("Bad OpCode Found: " + code);
            throw new Exception("constant folding failed");
        }
    }
}
