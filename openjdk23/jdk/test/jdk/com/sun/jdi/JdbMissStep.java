/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4762765
 * @summary REGRESSION: jdb / jdi not stopping at some breakpoints and steps in j2sdk1.4.
 * @comment converted from test/jdk/com/sun/jdi/JdbMissStep.sh
 *
 * @library /test/lib
 * @compile -g JdbMissStep.java
 * @run main/othervm JdbMissStep
 */

import jdk.test.lib.process.OutputAnalyzer;
import lib.jdb.JdbCommand;
import lib.jdb.JdbTest;

class JdbMissStepTarg {

    public static void main(String args[]) {
        JdbMissStepTarg dbb = new JdbMissStepTarg();
        System.out.println("ANSWER IS: " + dbb.getIntVal());
        jj2 gus = new jj2();
        System.out.println("ANSWER2 IS: " + gus.getIntVal());
    }

    static int statVal;
    int intVal = 89;
    public int getIntVal() {
        return intVal;  //@ 1 breakpoint
    }

    static class jj2 {
        static int statVal;
        int intVal = 89;
        public int getIntVal() {
            return intVal;  //@1 breakpoint  line 20
        }
    }
}

public class JdbMissStep extends JdbTest {
    public static void main(String argv[]) {
        new JdbMissStep().run();
    }

    private JdbMissStep() {
        super(DEBUGGEE_CLASS);
    }

    private static final String DEBUGGEE_CLASS = JdbMissStepTarg.class.getName();

    @Override
    protected void runCases() {
        setBreakpoints(jdb, DEBUGGEE_CLASS + "$jj2", System.getProperty("test.src") + "/JdbMissStep.java", 1);

        jdb.command(JdbCommand.run());
        jdb.command(JdbCommand.step());

        new OutputAnalyzer(jdb.getJdbOutput())
                .shouldContain("Breakpoint hit");
    }
}
