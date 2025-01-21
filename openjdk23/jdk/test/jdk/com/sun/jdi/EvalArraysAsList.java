/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8160024
 * @summary jdb returns invalid argument count if first parameter to Arrays.asList is null
 * @comment converted from test/jdk/com/sun/jdi/EvalArraysAsList.sh
 *
 * @library /test/lib
 * @build EvalArraysAsList
 * @run main/othervm EvalArraysAsList
 */

import lib.jdb.JdbCommand;
import lib.jdb.JdbTest;

/*
 * The test checks if evaluation of the expression java.util.Arrays.asList(null, "a")
 * works normally and does not throw an IllegalArgumentException.
 */
class EvalArraysAsListTarg {
    public static void main(String[] args) {
        java.util.List<Object> l = java.util.Arrays.asList(null, "a");
        System.out.println("java.util.Arrays.asList(null, \"a\") returns: " + l);
        return;    // @1 breakpoint
    }
}


public class EvalArraysAsList extends JdbTest {
    public static void main(String argv[]) {
        new EvalArraysAsList().run();
    }

    private EvalArraysAsList() {
        super(DEBUGGEE_CLASS);
    }

    private static final String DEBUGGEE_CLASS = EvalArraysAsListTarg.class.getName();

    @Override
    protected void runCases() {
        setBreakpointsFromTestSource("EvalArraysAsList.java", 1);
        // Run to breakpoint #1
        jdb.command(JdbCommand.run());

        final String illegalArgumentException = "IllegalArgumentException";

        evalShouldNotContain("java.util.Arrays.asList(null, null)", illegalArgumentException);

        evalShouldNotContain("java.util.Arrays.asList(null, \"a\")", illegalArgumentException);

        evalShouldNotContain("java.util.Arrays.asList(\"a\", null)", illegalArgumentException);

        jdb.contToExit(1);
    }

}
