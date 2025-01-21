/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4330479
 * @summary ASCII SUB character is rejected in multi-line comments
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main SubChar
 */

import toolbox.JavacTask;
import toolbox.JavaTask;
import toolbox.Task;
import toolbox.ToolBox;


public class SubChar {
    private static final ToolBox TOOLBOX = new ToolBox();

    private static final String SOURCE = """
        /*
        Note: this source file has been crafted very carefully to end with the
        unicode escape sequence for the control-Z character without a
        following newline.  The scanner is specified to allow control-Z there.
        If you edit this source file, please make sure that your editor does
        not insert a newline after that trailing line.
        */

        /** \\u001A */
        class ControlZTest {
            public static void main(String args[]) {
                return;
            }
        }
        /* \\u001A */\
        """;

        public static void main(String... args) {
            String output = new JavacTask(TOOLBOX)
                    .sources(SOURCE)
                    .classpath(".")
                    .options("-encoding", "utf8")
                    .run()
                    .writeAll()
                    .getOutput(Task.OutputKind.DIRECT);
            System.out.println(output);
        }
}
