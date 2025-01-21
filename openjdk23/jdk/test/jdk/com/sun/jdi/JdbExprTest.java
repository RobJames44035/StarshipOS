/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 4660158
 * @summary javac produces the inconsistent variable debug in while loops.
 * @comment converted from test/jdk/com/sun/jdi/JdbExprTest.sh
 *
 * @library /test/lib
 * @compile -g JdbExprTest.java
 * @run main/othervm JdbExprTest
 */

import lib.jdb.JdbCommand;
import lib.jdb.JdbTest;

import java.util.*;
import java.net.URLClassLoader;
import java.net.URL;

class JdbExprTestTarg {
    static Long lMax = new Long(java.lang.Long.MAX_VALUE); // force initialization of Long class
    static long aLong;
    static int anInt;
    static boolean aBoolean;

    public static void bkpt() {
        int i = 0;     //@1 breakpoint
    }

    public static void main(String[] args) {
        bkpt();
    }
}

public class JdbExprTest extends JdbTest {
    public static void main(String argv[]) {
        new JdbExprTest().run();
    }

    private JdbExprTest() {
        super(DEBUGGEE_CLASS);
    }

    private static final String DEBUGGEE_CLASS = JdbExprTestTarg.class.getName();

    @Override
    protected void runCases() {
        setBreakpointsFromTestSource("JdbExprTest.java", 1);
        // Run to breakpoint #1
        execCommand(JdbCommand.run())
                .shouldContain("Breakpoint hit");

        execCommand(JdbCommand.print("java.lang.Long.MAX_VALUE"))
                .shouldContain(" = 9223372036854775807");

        execCommand(JdbCommand.print("java.lang.Long.MIN_VALUE"))
                .shouldContain(" = -9223372036854775808");

        execCommand(JdbCommand.print("9223372036854775807L"))
                .shouldContain("9223372036854775807L = 9223372036854775807");
        execCommand(JdbCommand.print("9223372036854775807"))
                .shouldContain("9223372036854775807 = 9223372036854775807");

        execCommand(JdbCommand.print("-9223372036854775807L"))
                .shouldContain("-9223372036854775807L = -9223372036854775807");
        execCommand(JdbCommand.print("-9223372036854775807"))
                .shouldContain("-9223372036854775807 = -9223372036854775807");

        execCommand(JdbCommand.print("-1"))
                .shouldContain("-1 = -1");
        execCommand(JdbCommand.print("1L"))
                .shouldContain("1L = 1");
        execCommand(JdbCommand.print("-1L"))
                .shouldContain("-1L = -1");
        execCommand(JdbCommand.print("0x1"))
                .shouldContain("0x1 = 1");

        jdb.command(JdbCommand.set("JdbExprTestTarg.aLong", "9223372036854775807L"));
        execCommand(JdbCommand.print("JdbExprTestTarg.aLong"))
                .shouldContain("JdbExprTestTarg.aLong = 9223372036854775807");

        jdb.command(JdbCommand.set("JdbExprTestTarg.anInt", "java.lang.Integer.MAX_VALUE + 1"));
        execCommand(JdbCommand.print("JdbExprTestTarg.anInt"))
                .shouldContain("JdbExprTestTarg.anInt = -2147483648");

        jdb.command(JdbCommand.set("JdbExprTestTarg.aLong", "java.lang.Integer.MAX_VALUE + 1L"));
        execCommand(JdbCommand.print("JdbExprTestTarg.aLong"))
                .shouldContain("JdbExprTestTarg.aLong = 2147483648");

        execCommand(JdbCommand.set("JdbExprTestTarg.anInt", "0x80000000"))
                .shouldMatch("InvalidTypeException: .* convert 2147483648 to int");
        execCommand(JdbCommand.set("JdbExprTestTarg.anInt", "0x8000000000000000L"))
                .shouldContain("java.lang.NumberFormatException: For input string: \"8000000000000000\"");

        execCommand(JdbCommand.set("JdbExprTestTarg.anInt", "0x7fffffff"))
                .shouldContain("0x7fffffff = 2147483647");
        execCommand(JdbCommand.set("JdbExprTestTarg.aLong", "0x7fffffffffffffff"))
                .shouldContain("0x7fffffffffffffff = 9223372036854775807");

        execCommand(JdbCommand.print("3.1415"))
                .shouldContain("3.1415 = 3.1415");
        execCommand(JdbCommand.print("-3.1415"))
                .shouldContain("-3.1415 = -3.1415");
        execCommand(JdbCommand.print("011"))
                .shouldContain("011 = 9");

        execCommand(JdbCommand.set("JdbExprTestTarg.aBoolean", "false"))
                .shouldContain("JdbExprTestTarg.aBoolean = false = false");
        execCommand(JdbCommand.print("JdbExprTestTarg.aBoolean"))
                .shouldContain("JdbExprTestTarg.aBoolean = false");
        execCommand(JdbCommand.print("!JdbExprTestTarg.aBoolean"))
                .shouldContain("JdbExprTestTarg.aBoolean = true");

        execCommand(JdbCommand.print("~1"))
                .shouldContain("~1 = -2");
    }
}
