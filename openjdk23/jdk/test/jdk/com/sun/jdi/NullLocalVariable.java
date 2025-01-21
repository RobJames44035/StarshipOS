/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4690242 4695338
 * @summary TTY: jdb throws NullPointerException when printing local variables
 * @comment converted from test/jdk/com/sun/jdi/NullLocalVariable.sh
 *
 * @library /test/lib
 * @compile -g NullLocalVariable.java
 * @run main/othervm NullLocalVariable
 */

import jdk.test.lib.process.OutputAnalyzer;
import lib.jdb.JdbCommand;
import lib.jdb.JdbTest;

class NullLocalVariableTarg {
    public static final void main(String args[]) {
        try {
            System.out.println("hi!");               // @1 breakpoint
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("done");
        }
    }
}

public class NullLocalVariable extends JdbTest {
    public static void main(String argv[]) {
        new NullLocalVariable().run();
    }

    private NullLocalVariable() {
        super(DEBUGGEE_CLASS);
    }

    private static final String DEBUGGEE_CLASS = NullLocalVariableTarg.class.getName();

    @Override
    protected void runCases() {
        setBreakpointsFromTestSource("NullLocalVariable.java", 1);
        // Run to breakpoint #1
        jdb.command(JdbCommand.run());

        jdb.command(JdbCommand.next());
        jdb.command(JdbCommand.next());
        jdb.command(JdbCommand.locals());
        jdb.contToExit(1);

        new OutputAnalyzer(getDebuggeeOutput())
                .shouldNotContain("Internal exception");
    }
}
