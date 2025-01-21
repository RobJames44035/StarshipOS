/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @library /test/lib /
 * @bug 8281467
 * @requires vm.flagless
 * @requires os.arch=="amd64" | os.arch=="x86_64"
 *
 * @summary Test large OptoLoopAlignments are accepted
 * @run driver compiler.arguments.TestOptoLoopAlignment
 */

package compiler.arguments;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class TestOptoLoopAlignment {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            driver();
        } else {
            System.out.println("Pass");
        }
    }

    private static final String MSG = "must be less or equal to CodeEntryAlignment";

    private static List<String> cmdline(String[] args) {
        List<String> r = new ArrayList();
        r.addAll(Arrays.asList(args));
        r.add("compiler.arguments.TestOptoLoopAlignment");
        r.add("run");
        return r;
    }

    public static void shouldFail(String... args) throws IOException {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(cmdline(args));
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldNotHaveExitValue(0);
        output.shouldContain(MSG);
    }

    public static void shouldPass(String... args) throws IOException {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(cmdline(args));
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldHaveExitValue(0);
        output.shouldNotContain(MSG);
    }

    public static void driver() throws IOException {
        for (int align = 1; align < 64; align *= 2) {
            shouldPass(
                "-XX:OptoLoopAlignment=" + align
            );
        }
        for (int align = 64; align <= 128; align *= 2) {
            shouldFail(
                "-XX:OptoLoopAlignment=" + align
            );
        }
    }

}
