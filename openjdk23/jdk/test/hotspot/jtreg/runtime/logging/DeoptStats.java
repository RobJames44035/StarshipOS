/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @bug 8275865
 * @requires vm.compiler2.enabled
 * @summary Verify that the Deoptimization statistics are printed to the VM/Compiler log file
 * @library /test/lib
 * @run main/othervm -Xbatch -XX:-UseOnStackReplacement -XX:-OmitStackTraceInFastThrow
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation
 *                   -XX:-LogVMOutput -XX:LogFile=compilation.log DeoptStats
 * @run main/othervm -Xbatch -XX:-UseOnStackReplacement -XX:-OmitStackTraceInFastThrow
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation
 *                   -XX:+LogVMOutput -XX:LogFile=vmOutput.log DeoptStats
 * @run main/othervm DeoptStats compilation.log vmOutput.log
 */

import java.nio.file.Paths;
import jdk.test.lib.process.OutputAnalyzer;

public class DeoptStats {

    static class Value {
        int i;

        public Value(int i) { this.i = i; }
    }

    static int f(Value v) {
        try {
            return v.i;
        } catch (NullPointerException npe) {
            return -1;
        }
    }

    public static void verify(String[] logFiles) throws Exception {
        for (String logFile : logFiles) {
            OutputAnalyzer oa = new OutputAnalyzer(Paths.get(logFile));
            oa.shouldMatchByLine("<statistics type='deoptimization'>", // Start from this line
                                 "</statistics>",                      // Match until this line
                                 "(Deoptimization traps recorded:)|( .+)");
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            verify(args);
        } else {
            Value zero = new Value(0);
            for (int i = 0; i < 20_000; i++) {
                f(zero);
            }
            // trigger null_check
            f(null);
        }
    }
}
