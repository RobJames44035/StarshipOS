/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8173308
 * @summary Check JDK_JAVA_OPTIONS parsing behavior
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.main
 * @modules jdk.internal.opt/jdk.internal.opt
 * @build toolbox.ToolBox toolbox.TestRunner
 * @run main EnvVariableTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.List;

import toolbox.*;

import jdk.internal.opt.CommandLine;

public class EnvVariableTest extends TestRunner {
    final String testClasses;
    final ToolBox tb;
    final Path javaExePath;
    final ExecTask task;
    final PrintStream ostream;
    final ByteArrayOutputStream baos;

    public EnvVariableTest() {
        super(System.err);
        ostream = System.err;
        baos = new ByteArrayOutputStream();
        testClasses = System.getProperty("test.classes");
        tb = new ToolBox();
        javaExePath = tb.getJDKTool("java");
        task = new ExecTask(tb, javaExePath);
    }

    public static void main(String... args) throws Exception {
        EnvVariableTest t = new EnvVariableTest();
        t.runTests();
    }

    @Test
    public void testDoubleQuote() throws Exception {
        // white space quoted with double quotes
        test("-version -cp \"c:\\\\java libs\\\\one.jar\" \n",
                "-version", "-cp", "c:\\\\java libs\\\\one.jar");
    }

    @Test
    public void testSingleQuote() throws Exception {
        // white space quoted with single quotes
        test("-version -cp \'c:\\\\java libs\\\\one.jar\' \n",
                "-version", "-cp", "c:\\\\java libs\\\\one.jar");
    }

    @Test
    public void testEscapeCharacters() throws Exception {
        // escaped characters
        test("escaped chars testing \"\\a\\b\\c\\f\\n\\r\\t\\v\\9\\6\\23\\82\\28\\377\\477\\278\\287\"",
                "escaped", "chars", "testing", "\\a\\b\\c\\f\\n\\r\\t\\v\\9\\6\\23\\82\\28\\377\\477\\278\\287");
    }

    @Test
    public void testMixedQuotes() throws Exception {
        // more mixing of quote types
        test("\"mix 'single quote' in double\" 'mix \"double quote\" in single' partial\"quote me\"this",
                "mix 'single quote' in double", "mix \"double quote\" in single", "partialquote methis");
    }

    @Test
    public void testWhiteSpaces() throws Exception {
        // whitespace tests
        test("line one #comment\n'line #2' #rest are comment\r\n#comment on line 3\fline 4 #comment to eof",
                "line", "one", "#comment", "line #2", "#rest", "are", "comment", "#comment", "on", "line",
                "3", "line", "4", "#comment", "to", "eof");
    }

    @Test
    public void testMismatchedDoubleQuote() throws Exception {
        // mismatched quote
        test("This is an \"open quote \n    across line\n\t, note for WS.",
                "Exception: JDK_JAVAC_OPTIONS");
    }

    @Test
    public void testMismatchedSingleQuote() throws Exception {
        // mismatched quote
        test("This is an \'open quote \n    across line\n\t, note for WS.",
                "Exception: JDK_JAVAC_OPTIONS");
    }

    void test(String full, String... expectedArgs) throws Exception {
        task.envVar("JDK_JAVAC_OPTIONS", full);
        task.args(
                "--add-exports", "jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED",
                "--add-exports", "jdk.internal.opt/jdk.internal.opt=ALL-UNNAMED",
                "-cp", testClasses, "EnvVariableTest$Tester");
        Task.Result tr = task.run(Task.Expect.SUCCESS);
        String expected = Tester.arrayToString(expectedArgs);
        String in = tr.getOutput(Task.OutputKind.STDOUT);
        System.err.println("Matching...");
        System.err.println("Obtained: " + in);
        System.err.println("Expected: " + expected);
        if (in.contains(expected)) {
            System.err.println("....OK");
            return;
        }
        throw new Exception("Expected strings not found");
    }

    /**
     * A tester class that is invoked to invoke the CommandLine class, and
     * print the result.
     */
    public static class Tester {
        private static final List<String> EMPTY_LIST = List.of();
        static String arrayToString(String... args) {
            return List.of(args).stream().collect(Collectors.joining(", "));
        }
        public static void main(String... args) throws IOException {
            try {
                List<String> argv = CommandLine.parse("JDK_JAVAC_OPTIONS", EMPTY_LIST);
                System.out.print(argv.stream().collect(Collectors.joining(", ")));
            } catch (CommandLine.UnmatchedQuote ex) {
                System.out.print("Exception: " + ex.variableName);
            }
        }
    }
}
