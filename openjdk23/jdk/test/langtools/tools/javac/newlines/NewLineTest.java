/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 4110560 4785453
 * @summary portability : javac.properties
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main NewLineTest
 */

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import toolbox.JavacTask;
import toolbox.Task;
import toolbox.ToolBox;

//original test: test/tools/javac/newlines/Newlines.sh
/*
 * Checks that the usage message, contained in the properties in the
 * resource file javac.properties, is correctly rendered, including
 * embedded newlines in the resource strings. For more context,
 * see JDK-4110560.
 */
public class NewLineTest {

    public static void main(String args[]) throws Exception {
        ToolBox tb = new ToolBox();
        File javacOutput = new File("output.txt");
        new JavacTask(tb, Task.Mode.EXEC)
                .redirect(Task.OutputKind.STDOUT, javacOutput.getPath())
                .options("-J-Dline.separator='@'")
                .run(Task.Expect.FAIL);

        String encoding = System.getProperty("native.encoding");
        Charset cs = (encoding != null) ? Charset.forName(encoding) : Charset.defaultCharset();
        List<String> lines = Files.readAllLines(javacOutput.toPath(), cs);
        if (lines.size() != 1) {
            throw new AssertionError("The compiler output should have one line only");
        }
    }

}
