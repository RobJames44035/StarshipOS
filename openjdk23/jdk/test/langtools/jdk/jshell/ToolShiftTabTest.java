/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8166334 8188894
 * @summary test shift-tab shortcuts "fixes"
 * @modules
 *     jdk.jshell/jdk.internal.jshell.tool:open
 *     jdk.jshell/jdk.internal.jshell.tool.resources:open
 *     jdk.jshell/jdk.jshell:open
 * @build UITesting
 * @build ToolShiftTabTest
 * @run testng/timeout=300 ToolShiftTabTest
 */

import java.util.regex.Pattern;
import org.testng.annotations.Test;

@Test
public class ToolShiftTabTest extends UITesting {

    // Shift-tab as escape sequence
    private String FIX = "\033\133\132";

    public void testFixVariable() throws Exception {
        doRunTest((inputSink, out) -> {
            inputSink.write("3+4");
            inputSink.write(FIX + "v");
            inputSink.write("jj\n");
            waitOutput(out, "jj ==> 7");
            inputSink.write("jj\n");
            waitOutput(out, "jj ==> 7");
        });
    }

    public void testFixMethod() throws Exception {
        doRunTest((inputSink, out) -> {
            inputSink.write("5.5 >= 3.1415926535");
            inputSink.write(FIX + "m");
            waitOutput(out, "boolean ");
            inputSink.write("mm\n");
            waitOutput(out, "|  created method mm()");
            inputSink.write("mm()\n");
            waitOutput(out, "==> true");
            inputSink.write("/method\n");
            waitOutput(out, "boolean mm()");
        });
    }

    public void testFixMethodVoid() throws Exception {
        doRunTest((inputSink, out) -> {
            inputSink.write("System.out.println(\"Testing\")");
            inputSink.write(FIX + "m");
            inputSink.write("p\n");
            waitOutput(out, "|  created method p()");
            inputSink.write("p()\n");
            waitOutput(out, "Testing");
            inputSink.write("/method\n");
            waitOutput(out, "void p()");
        });
    }

    public void testFixMethodNoLeaks() throws Exception {
        doRunTest((inputSink, out) -> {
            inputSink.write("4");
            inputSink.write(FIX + "m");
            inputSink.write(INTERRUPT + " 55");
            inputSink.write(FIX + "m");
            inputSink.write(INTERRUPT + " 55");
            inputSink.write(FIX + "m");
            inputSink.write(INTERRUPT + " 55");
            inputSink.write(FIX + "m");
            inputSink.write(INTERRUPT + " 55");
            inputSink.write(FIX + "m");
            inputSink.write(INTERRUPT + "'X'");
            inputSink.write(FIX + "m");
            inputSink.write("nl\n");
            waitOutput(out, "|  created method nl()");
            inputSink.write("/list\n");
            waitOutput(out, Pattern.quote("1 : char nl() { return 'X'; }"));
            inputSink.write("true\n");
            waitOutput(out, Pattern.quote("$2 ==> true"));
            inputSink.write("/list\n");
            waitOutput(out, "2 : true");
        });
    }

    public void testFixImport() throws Exception {
        doRunTest((inputSink, out) -> {
            inputSink.write("Frame");
            inputSink.write(FIX + "i");
            while (!waitOutput(out, "java.awt.Frame", "Results may be incomplete")) {
                Thread.sleep(1000);
                inputSink.write(FIX + "i");
            }
            inputSink.write("1");
            inputSink.write(".WIDTH\n");
            waitOutput(out, "==> 1");
            inputSink.write("/import\n");
            waitOutput(out, "|    import java.awt.Frame");

            inputSink.write("Object");
            inputSink.write(FIX + "i");
            waitOutput(out, "The identifier is resolvable in this context");
        });
    }

    public void testFixBad() throws Exception {
        doRunTest((inputSink, out) -> {
            inputSink.write("123");
            inputSink.write(FIX + "z");
            waitOutput(out, "Unexpected character after Shift\\+Tab");
        });
    }
}
