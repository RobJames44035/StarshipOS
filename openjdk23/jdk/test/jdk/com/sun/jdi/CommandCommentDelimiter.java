/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4507088
 * @summary TTY: Add a comment delimiter to the jdb command set
 * @comment converted from test/jdk/com/sun/jdi/CommandCommentDelimiter.sh
 *
 * @library /test/lib
 * @build CommandCommentDelimiter
 * @run main/othervm CommandCommentDelimiter
 */

import jdk.test.lib.process.OutputAnalyzer;
import lib.jdb.JdbCommand;
import lib.jdb.JdbTest;

class CommandCommentDelimiterTarg {
    public static void main(String args[]) {
        System.out.print  ("Hello");
        System.out.print  (", ");
        System.out.print  ("world");
        System.out.println("!");
    }
}


public class CommandCommentDelimiter extends JdbTest {
    public static void main(String argv[]) {
        new CommandCommentDelimiter().run();
    }

    private CommandCommentDelimiter() {
        super(DEBUGGEE_CLASS);
    }

    private static final String DEBUGGEE_CLASS = CommandCommentDelimiterTarg.class.getName();

    @Override
    protected void runCases() {
        jdb.command(JdbCommand.stopIn(DEBUGGEE_CLASS, "main"));
        jdb.command(JdbCommand.run());

        jdb.command(JdbCommand.step());
        jdb.command("#");
        jdb.command("#foo");
        jdb.command("3 #blah");
        jdb.command("# connectors");
        jdb.command(JdbCommand.step());

        jdb.contToExit(1);

        new OutputAnalyzer(getJdbOutput())
                .shouldNotContain("Unrecognized command: '#'.  Try help...")
                .shouldNotContain("Available connectors are");
    }

}
