/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6173560
 * @summary Redefine a class that implements an interface
 *          and verify that a subclass calls the right method.
 * @comment converted from test/jdk/com/sun/jdi/RedefineImplementor.sh
 *
 * @library /test/lib
 * @compile -g RedefineImplementor.java
 * @run main/othervm RedefineImplementor
 */

import jdk.test.lib.process.OutputAnalyzer;
import lib.jdb.JdbCommand;
import lib.jdb.JdbTest;

class RedefineImplementorTarg implements Runnable {
    public void run() {
        System.out.println("RedefineImplementorTarg's run");
        // @1 uncomment System.out.println("This is the new version of RedefineImplementorTarg");
    }

    public static void main(String[] args) {
        Runnable r = new RedefineImplementorB();
        RedefineImplementorB.func(r);
        RedefineImplementorB.func(r);  // @1 breakpoint
    }

}

class RedefineImplementorB extends RedefineImplementorTarg {
    static void func(Runnable r) {
        r.run();
    }
}

public class RedefineImplementor extends JdbTest {
    public static void main(String argv[]) {
        new RedefineImplementor().run();
    }

    private RedefineImplementor() {
        super(RedefineImplementorTarg.class.getName(),
                "RedefineImplementor.java");
    }

    @Override
    protected void runCases() {
        setBreakpoints(1);
        jdb.command(JdbCommand.run());

        redefineClass(1, "-g");

        jdb.contToExit(1);

        new OutputAnalyzer(getDebuggeeOutput())
                .shouldContain("This is the new version of ");
    }
}
