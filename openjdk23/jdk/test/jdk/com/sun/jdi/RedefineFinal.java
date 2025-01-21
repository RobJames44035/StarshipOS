/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4788344
 * @summary RedefineClasses is an apparent no-op if instance method is final
 * @comment converted from test/jdk/com/sun/jdi/RedefineFinal.sh
 *
 * @library /test/lib
 * @compile -g RedefineFinal.java
 * @run main/othervm RedefineFinal
 */

import jdk.test.lib.process.OutputAnalyzer;
import lib.jdb.JdbCommand;
import lib.jdb.JdbTest;

final class RedefineFinalTarg {

    public int m1(int i) {
        // @1 uncomment System.out.println("I'm here");
        return m2(i, 1000);
    }

    public int m2(int i, int j) {
        if (i < 0 || j < 0) {   // @1 breakpoint
            throw new IllegalArgumentException();
        }
        return i+j;
    }

    RedefineFinalTarg() {
        m1(0);
        m1(0);
    }

    public static void main(String args[]) {
        new RedefineFinalTarg();
    }
}

public class RedefineFinal extends JdbTest {

    public static void main(String argv[]) {
        new RedefineFinal().run();
    }

    private RedefineFinal() {
        super(RedefineFinalTarg.class.getName(), "RedefineFinal.java");
    }

    @Override
    protected void runCases() {
        setBreakpoints(1);
        jdb.command(JdbCommand.run());
        redefineClass(1, "-g");
        setBreakpoints(1);
        jdb.command(JdbCommand.cont());
        jdb.command(JdbCommand.where(""));
        jdb.contToExit(1);

        new OutputAnalyzer(getJdbOutput())
                .shouldNotContain("obsolete");
    }
}
