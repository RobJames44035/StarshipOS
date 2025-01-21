/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8177076 8185840 8178109 8192863
 * @modules
 *     jdk.compiler/com.sun.tools.javac.api
 *     jdk.compiler/com.sun.tools.javac.main
 *     jdk.jshell/jdk.internal.jshell.tool:open
 *     jdk.jshell/jdk.internal.jshell.tool.resources:open
 *     jdk.jshell/jdk.jshell:open
 * @library /tools/lib
 * @build toolbox.ToolBox toolbox.JarTask toolbox.JavacTask
 * @build Compiler UITesting
 * @build ToolTabCommandTest
 * @run testng ToolTabCommandTest
 */

import org.testng.annotations.Test;

@Test
public class ToolTabCommandTest extends UITesting {

    public ToolTabCommandTest() {
        super(true);
    }

    public void testCommand() throws Exception {
        // set terminal height so that help output won't hit page breaks
        System.setProperty("test.terminal.height", "1000000");

        doRunTest((inputSink, out) -> {
            inputSink.write("1\n");
            waitOutput(out, PROMPT);
            inputSink.write("/" + TAB);
            waitOutput(out, ".*/edit.*/list.*\n\n" + resource("jshell.console.see.synopsis") +
                            REDRAW_PROMPT + "/");
            inputSink.write(TAB);
            waitOutput(out,   ".*\n/edit\n" + resource("help.edit.summary") +
                            "\n.*\n/list\n" + resource("help.list.summary") +
                            ".*\n\n" + resource("jshell.console.see.full.documentation") +
                            REDRAW_PROMPT + "/");
            inputSink.write(TAB);
            waitOutput(out,  "/!\n" +
                            resource("help.bang") + "\n" +
                            "\n" +
                            resource("jshell.console.see.next.command.doc") +
                            REDRAW_PROMPT + "/");
            inputSink.write(TAB);
            waitOutput(out,  "/-<n>\n" +
                            resource("help.previous") + "\n" +
                            "\n" +
                            resource("jshell.console.see.next.command.doc") +
                            REDRAW_PROMPT + "/");

            inputSink.write("ed" + TAB);
            waitOutput(out, "edit $");

            inputSink.write(TAB);
            waitOutput(out, ".*-all.*" +
                            "\n\n" + resource("jshell.console.see.synopsis") +
                            REDRAW_PROMPT + "/");
            inputSink.write(TAB);
            waitOutput(out, resource("help.edit.summary") + "\n\n" +
                            resource("jshell.console.see.full.documentation") +
                            REDRAW_PROMPT + "/edit ");
            inputSink.write(TAB);
            waitOutput(out, resource("help.edit"));

            inputSink.write(INTERRUPT + "/env " + TAB);
            waitOutput(out, PROMPT + "/env \n" +
                            "-add-exports    -add-modules    -class-path     -module-path    \n" +
                            "\n" +
                            resource("jshell.console.see.synopsis") +
                            REDRAW_PROMPT + "/env -");

            inputSink.write(TAB);
            waitOutput(out, resource("help.env.summary") + "\n\n" +
                            resource("jshell.console.see.full.documentation") +
                            REDRAW_PROMPT + "/env -");

            inputSink.write(TAB);
            waitOutput(out, resource("help.env") +
                            REDRAW_PROMPT + "/env -");

            inputSink.write(TAB);
            waitOutput(out, "-add-exports    -add-modules    -class-path     -module-path    \n" +
                            "\n" +
                            resource("jshell.console.see.synopsis") +
                            REDRAW_PROMPT + "/env -");

            inputSink.write(INTERRUPT + "/exit " + TAB);
            waitOutput(out, resource("help.exit.summary") + "\n\n" +
                            resource("jshell.console.see.full.documentation") +
                            REDRAW_PROMPT + "/exit ");
            inputSink.write(TAB);
            waitOutput(out, resource("help.exit") +
                            REDRAW_PROMPT + "/exit ");
            inputSink.write(TAB);
            waitOutput(out, resource("help.exit.summary") + "\n\n" +
                            resource("jshell.console.see.full.documentation") +
                            REDRAW_PROMPT + "/exit ");
            inputSink.write(INTERRUPT);
            inputSink.write("int zebraStripes = 11\n");
            waitOutput(out, "\\u001B\\[\\?2004lzebraStripes ==> 11\n\\u001B\\[\\?2004h" + PROMPT);
            inputSink.write("/exit zeb" + TAB);
            waitOutput(out, "braStr.*es");
            inputSink.write(INTERRUPT + "/doesnotexist" + TAB);
            waitOutput(out, PROMPT + "/doesnotexist\n" +
                            resource("jshell.console.no.such.command") + "\n" +
                            REDRAW_PROMPT + "/doesnotexist");
        });
    }

    public void testRerunCommands() throws Exception {
        // set terminal height so that help output won't hit page breaks
        System.setProperty("test.terminal.height", "1000000");

        doRunTest((inputSink, out) -> {
            inputSink.write("1\n");
            waitOutput(out, PROMPT);
            inputSink.write("2\n");
            waitOutput(out, PROMPT);

            inputSink.write("/1" + TAB);
            waitOutput(out, resource("help.rerun.summary") + "\n\n" +
                            resource("jshell.console.see.full.documentation") +
                            REDRAW_PROMPT + "/1");
            inputSink.write(TAB);
            waitOutput(out, resource("help.rerun") +
                            REDRAW_PROMPT + "/1");
            inputSink.write(TAB);
            waitOutput(out, resource("help.rerun.summary") + "\n\n" +
                            resource("jshell.console.see.full.documentation") +
                            REDRAW_PROMPT + "/1");

            inputSink.write(INTERRUPT);
            inputSink.write("/-1" + TAB);
            waitOutput(out, resource("help.rerun.summary") + "\n\n" +
                            resource("jshell.console.see.full.documentation") +
                            REDRAW_PROMPT + "/-1");
            inputSink.write(TAB);
            waitOutput(out, resource("help.rerun") +
                            REDRAW_PROMPT + "/-1");
            inputSink.write(TAB);
            waitOutput(out, resource("help.rerun.summary") + "\n\n" +
                            resource("jshell.console.see.full.documentation") +
                            REDRAW_PROMPT + "/-1");
        });
    }

    public void testHelp() throws Exception {
        // set terminal height so that help output won't hit page breaks
        System.setProperty("test.terminal.height", "1000000");

        doRunTest((inputSink, out) -> {
            inputSink.write("/help " + TAB);
            waitOutput(out, ".*/edit.*/list.*intro.*\n\n" + resource("jshell.console.see.synopsis") +
                            REDRAW_PROMPT + "/");
            inputSink.write(TAB);
            waitOutput(out,   ".*\n/edit\n" + resource("help.edit.summary") +
                            "\n.*\n/list\n" + resource("help.list.summary") +
                            "\n.*\nintro\n" + resource("help.intro.summary") +
                            ".*\n\n" + resource("jshell.console.see.full.documentation") +
                            REDRAW_PROMPT + "/");
            inputSink.write("/env" + TAB);
            waitOutput(out,   "help /env ");
            inputSink.write(TAB);
            waitOutput(out,   ".*\n/env\n" + resource("help.env.summary") +
                            ".*\n\n" + resource("jshell.console.see.full.documentation") +
                            REDRAW_PROMPT + "/help /env ");
            inputSink.write(TAB);
            waitOutput(out,   ".*\n/env\n" + resource("help.env") +
                            REDRAW_PROMPT + "/help /env ");
            inputSink.write(INTERRUPT + "/help intro" + TAB);
            waitOutput(out,   "help intro ");
            inputSink.write(TAB);
            waitOutput(out,   ".*\nintro\n" + resource("help.intro.summary") +
                            ".*\n\n" + resource("jshell.console.see.full.documentation") +
                            REDRAW_PROMPT + "/help intro ");
            inputSink.write(TAB);
            waitOutput(out,   ".*\nintro\n" + resource("help.intro") +
                            REDRAW_PROMPT + "/help intro ");
            inputSink.write(INTERRUPT + "/help /set " + TAB);
            waitOutput(out, ".*format.*truncation.*\n\n" + resource("jshell.console.see.synopsis") +
                            REDRAW_PROMPT + "/help /set ");
            inputSink.write(TAB);
            waitOutput(out,   ".*\n/set format\n" + resource("help.set.format.summary") +
                            "\n.*\n/set truncation\n" + resource("help.set.truncation.summary") +
                            ".*\n\n" + resource("jshell.console.see.full.documentation") +
                            REDRAW_PROMPT + "/help /set ");
            inputSink.write("truncation" + TAB);
            waitOutput(out,   ".*truncation\n" + resource("jshell.console.see.synopsis") +
                            REDRAW_PROMPT + "/help /set truncation");
            inputSink.write(TAB);
            waitOutput(out,   ".*/set truncation\n" + resource("help.set.truncation.summary") + "\n" +
                            "\n" + resource("jshell.console.see.full.documentation") +
                            REDRAW_PROMPT + "/help /set truncation");
            inputSink.write(TAB);
            waitOutput(out,   ".*/set truncation\n" + resource("help.set.truncation") +
                           "\r" + PROMPT + "/help /set truncation");
            inputSink.write(INTERRUPT + "/help env " + TAB);
            waitOutput(out,   ".*\n/env\n" + resource("help.env.summary") +
                            ".*\n\n" + resource("jshell.console.see.full.documentation") +
                            REDRAW_PROMPT + "/help env ");
            inputSink.write(INTERRUPT + "/help set truncation" + TAB);
            waitOutput(out,   ".*truncation\n" + resource("jshell.console.see.synopsis") +
                            REDRAW_PROMPT + "/help set truncation");
            inputSink.write(TAB);
            waitOutput(out,   ".*\n/set truncation\n" + resource("help.set.truncation.summary") +
                            ".*\n\n" + resource("jshell.console.see.full.documentation") +
                            REDRAW_PROMPT + "/help set truncation");
        });
    }
}
