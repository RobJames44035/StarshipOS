/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4546478
 * @summary Enabling a watchpoint can kill following NotifyFramePops
 * @comment converted from test/jdk/com/sun/jdi/WatchFramePop.sh
 *
 * @library /test/lib
 * @run main/othervm WatchFramePop
 */

import jdk.test.lib.process.OutputAnalyzer;
import lib.jdb.JdbCommand;
import lib.jdb.JdbTest;

class WatchFramePopTarg {
    int watchMe;
    static public void main(String[] args) {
        System.out.println("In Main");
        WatchFramePopTarg mine = new WatchFramePopTarg();
        mine.a1();
        System.out.println("Test completed");
    }

    public void a1() {
        a2();                           // @1 breakpoint. We'll do a watch of watchMe here
    }

    public void a2() {
        System.out.println("in a2");
        a3();
    }                                   // line 18

    public void a3() {
        System.out.println("in a3");    // After the watch, we'll run to here, line 21
        a4();                           // We'll do a 'next' to here.  The failure is that this
    }                                   // runs to completion, or asserts with java_g

    public void a4() {
        System.out.println("in a4");
    }

}

public class WatchFramePop extends JdbTest {
    public static void main(String argv[]) {
        new WatchFramePop().run();
    }

    private WatchFramePop() {
        super(DEBUGGEE_CLASS, SOURCE_FILE);
    }

    private static final String DEBUGGEE_CLASS = WatchFramePopTarg.class.getName();
    private static final String SOURCE_FILE = "WatchFramePop.java";

    @Override
    protected void runCases() {
        setBreakpoints(1);
        jdb.command(JdbCommand.run());
        jdb.command(JdbCommand.watch(DEBUGGEE_CLASS, "watchMe"));
        jdb.command(JdbCommand.stopIn(DEBUGGEE_CLASS, "a3"));
        jdb.command(JdbCommand.cont());                             // stops at the bkpt
        jdb.command(JdbCommand.next());                             // The bug is that this next runs to completion
        // In which case, so does jdb
        // so we never get here.

        new OutputAnalyzer(jdb.getJdbOutput())
                .shouldNotContain("The application exited");
    }
}
