/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4684386
 * @summary TTY: jdb throws IllegalArumentException on cmd line args
 * @comment converted from test/jdk/com/sun/jdi/JdbArgTest.sh
 *
 * @library /test/lib
 * @run main/othervm JdbArgTest
 */

import jdk.test.lib.process.OutputAnalyzer;
import lib.jdb.Jdb;

public class JdbArgTest {
    public static void main(String argv[]) throws Exception {
        try (Jdb jdb = new Jdb("Server", "0RBDebug", "subcontract,shutdown,transport")) {
            jdb.waitForSimplePrompt(1, true);
            jdb.quit();
            new OutputAnalyzer(jdb.getJdbOutput())
                    .shouldNotContain("IllegalArgumentException");
        }
    }
}
