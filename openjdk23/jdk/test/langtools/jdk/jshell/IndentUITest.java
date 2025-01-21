/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8241950 8247932
 * @summary Check the UI behavior of indentation
 * @library /tools/lib
 * @modules
 *     jdk.compiler/com.sun.tools.javac.api
 *     jdk.compiler/com.sun.tools.javac.main
 *     jdk.jshell/jdk.internal.jshell.tool:open
 *     jdk.jshell/jdk.internal.jshell.tool.resources:open
 *     jdk.jshell/jdk.jshell:open
 * @build toolbox.ToolBox toolbox.JarTask toolbox.JavacTask
 * @build Compiler UITesting
 * @compile IndentUITest.java
 * @run testng IndentUITest
 */

import org.testng.annotations.Test;

@Test
public class IndentUITest extends UITesting {

    public IndentUITest() {
        super(true);
    }

    public void testIdent() throws Exception {
        doRunTest((inputSink, out) -> {
            inputSink.write("void test1() {\nSystem.err.println(1);\n}\n");
            waitOutput(out, "void test1\\(\\)\u001B\\[2D\u001B\\[2C \\{\n" +
                            CONTINUATION_PROMPT + "    System.err.println\\(1\\)\u001B\\[3D\u001B\\[3C;\n" +
                            CONTINUATION_PROMPT + "    \\}\u001B\\[2A\u001B\\[8C\n\n\u001B\\[K\\}\n" +
                            "\u001B\\[\\?2004l\\|  created method test1\\(\\)\n" +
                            "\u001B\\[\\?2004h" + PROMPT);
            inputSink.write(UP);
            waitOutput(out, "^void test1\\(\\) \\{\n" +
                            CONTINUATION_PROMPT + "    System.err.println\\(1\\);\n" +
                            CONTINUATION_PROMPT + "\\}");
            inputSink.write(DOWN);
            inputSink.write("/set indent 2\n");
            inputSink.write("void test2() {\nSystem.err.println(1);\n}\n");
            waitOutput(out, "void test2\\(\\)\u001B\\[2D\u001B\\[2C \\{\n" +
                            CONTINUATION_PROMPT + "  System.err.println\\(1\\)\u001B\\[3D\u001B\\[3C;\n" +
                            CONTINUATION_PROMPT + "  \\}\u001B\\[2A\u001B\\[10C\n\n\u001B\\[K\\}\n" +
                            "\u001B\\[\\?2004l\\|  created method test2\\(\\)\n" +
                            "\u001B\\[\\?2004h" + PROMPT);
            inputSink.write(UP);
            waitOutput(out, "^void test2\\(\\) \\{\n" +
                            CONTINUATION_PROMPT + "  System.err.println\\(1\\);\n" +
                            CONTINUATION_PROMPT + "\\}");
            inputSink.write(INTERRUPT);
            waitOutput(out, "\u001B\\[\\?2004h" + PROMPT);
            inputSink.write("\"\"\"\n");
            waitOutput(out, "^\"\"\"\n" +
                            CONTINUATION_PROMPT);
        });
    }

}
