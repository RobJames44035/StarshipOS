/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8325567 8325621
 * @requires (os.family == "linux") | (os.family == "aix") | (os.family == "mac")
 * @library /test/lib
 * @run driver JspawnhelperWarnings
 */

import java.nio.file.Paths;
import java.util.Arrays;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class JspawnhelperWarnings {

    private static void tryWithNArgs(int nArgs) throws Exception {
        System.out.println("Running jspawnhelper with " + nArgs + " args");
        String[] args = new String[nArgs + 1];
        Arrays.fill(args, "1");
        args[0] = Paths.get(System.getProperty("java.home"), "lib", "jspawnhelper").toString();
        Process p = ProcessTools.startProcess("jspawnhelper", new ProcessBuilder(args));
        OutputAnalyzer oa = new OutputAnalyzer(p);
        oa.shouldHaveExitValue(1);
        oa.shouldContain("This command is not for general use");
        if (nArgs != 2) {
            oa.shouldContain("Incorrect number of arguments");
        } else {
            oa.shouldContain("Incorrect Java version");
        }
    }

    private static void testVersion() throws Exception {
        String[] args = new String[3];
        args[0] = Paths.get(System.getProperty("java.home"), "lib", "jspawnhelper").toString();
        args[1] = "wrongVersion";
        args[2] = "1:1:1";
        Process p = ProcessTools.startProcess("jspawnhelper", new ProcessBuilder(args));
        OutputAnalyzer oa = new OutputAnalyzer(p);
        oa.shouldHaveExitValue(1);
        oa.shouldContain("This command is not for general use");
        oa.shouldContain("Incorrect Java version: wrongVersion");
    }

    public static void main(String[] args) throws Exception {
        for (int nArgs = 0; nArgs < 10; nArgs++) {
            tryWithNArgs(nArgs);
        }

        testVersion();
    }
}
