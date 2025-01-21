/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7190813 8022719
 * @summary Check for extended RPATHs on Linux
 * @requires os.family == "linux"
 * @compile -XDignore.symbol.file RunpathTest.java
 * @run main RunpathTest
 * @author ksrini
 */

import java.io.File;

public class RunpathTest extends TestHelper {

    final String elfreaderCmd;
    RunpathTest() {
        elfreaderCmd = findElfReader();
    }

    final String findElfReader() {
        String[] paths = {"/usr/sbin", "/usr/bin"};
        final String cmd = "readelf";
        for (String x : paths) {
            File p = new File(x);
            File e = new File(p, cmd);
            if (e.canExecute()) {
                return e.getAbsolutePath();
            }
        }
        System.err.println("Warning: no suitable elf reader!");
        return null;
    }

    void elfCheck(String javacmd, String expectedRpath) {
        final TestResult tr = doExec(elfreaderCmd, "-d", javacmd);
        if (!tr.matches(expectedRpath)) {
            System.out.println(tr);
            throw new RuntimeException("FAILED: RPATH strings " +
                    expectedRpath + " not found in " + javaCmd);
        }
        System.out.println(javacmd + " contains expected RPATHS");
    }

    void testRpath() {
        String expectedRpath = ".*RPATH.*\\$ORIGIN/../lib.*";
        elfCheck(javaCmd, expectedRpath);
    }

    public static void main(String... args) throws Exception {
        RunpathTest rp = new RunpathTest();
        if (rp.elfreaderCmd == null) {
            System.err.println("Warning: test passes vacuously");
            return;
        }
        rp.testRpath();
    }
}
