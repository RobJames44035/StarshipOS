/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8149743
 * @summary crash when adding a breakpoint after redefining to add a private static method
 * @comment converted from test/jdk/com/sun/jdi/RedefineAddPrivateMethod.sh
 *
 * @library /test/lib
 * @compile -g RedefineAddPrivateMethod.java
 * @run main/othervm RedefineAddPrivateMethod
 */

import jdk.test.lib.process.OutputAnalyzer;
import lib.jdb.JdbCommand;
import lib.jdb.JdbTest;

class RedefineAddPrivateMethodTarg {
    static public void main(String[] args) {
        System.out.println("@1 breakpoint");
        System.out.println("@2 breakpoint");
    }

    // @1 uncomment private static void test() {}
}

public class RedefineAddPrivateMethod extends JdbTest {
    static private final String ALLOW_ADD_DELETE_OPTION = "-XX:+AllowRedefinitionToAddDeleteMethods";

    public static void main(String argv[]) {
        RedefineAddPrivateMethod test = new RedefineAddPrivateMethod();
        test.launchOptions.addVMOptions(ALLOW_ADD_DELETE_OPTION);
        test.run();
    }

    private RedefineAddPrivateMethod() {
        super(DEBUGGEE_CLASS, SOURCE_FILE);
    }

    private static final String DEBUGGEE_CLASS = RedefineAddPrivateMethodTarg.class.getName();
    private static final String SOURCE_FILE = "RedefineAddPrivateMethod.java";

    @Override
    protected void runCases() {
        setBreakpoints(1);
        jdb.command(JdbCommand.run());

        redefineClass(1, "-g");
        // ensure "test()" method has been added successfully
        execCommand(JdbCommand.eval(DEBUGGEE_CLASS + ".test()"))
                .shouldNotContain("ParseException");

        setBreakpoints(2);
        jdb.command(JdbCommand.run());

        jdb.quit();

        new OutputAnalyzer(getDebuggeeOutput())
                .shouldNotContain("Internal exception:");
    }
}
