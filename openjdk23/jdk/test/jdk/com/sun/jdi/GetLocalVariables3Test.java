/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4448658
 * @summary javac produces the inconsistent variable debug in while loops.
 * @comment converted from test/jdk/com/sun/jdi/GetLocalVariables3Test.sh
 *
 * @library /test/lib
 * @compile -g GetLocalVariables3Test.java
 * @run main/othervm GetLocalVariables3Test
 */

import jdk.test.lib.process.OutputAnalyzer;
import lib.jdb.JdbCommand;
import lib.jdb.JdbTest;


class GetLocalVariables3Targ {
    public static void main(String[] args) {
        System.out.println("Howdy!");
        int i = 1, j, k;
        while ((j = i) > 0) {
            k = j; i = k - 1;    // @1 breakpoint
        }
        System.out.println("Goodbye from GetLocalVariables3Targ!");
    }
}


public class GetLocalVariables3Test extends JdbTest {
    public static void main(String argv[]) {
        new GetLocalVariables3Test().run();
    }

    private GetLocalVariables3Test() {
        super(DEBUGGEE_CLASS);
    }

    private static final String DEBUGGEE_CLASS = GetLocalVariables3Targ.class.getName();

    @Override
    protected void runCases() {
        setBreakpointsFromTestSource("GetLocalVariables3Test.java", 1);
        // Run to breakpoint #1
        jdb.command(JdbCommand.run());

        jdb.command(JdbCommand.locals());

        jdb.contToExit(1);

        new OutputAnalyzer(getJdbOutput())
                .shouldContain("j = 1");
        new OutputAnalyzer(getDebuggeeOutput())
                .shouldContain("Howdy")
                .shouldContain("Goodbye from GetLocalVariables3Targ");
    }

}
