/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package gc.arguments;

/*
 * @test TestVerifyBeforeAndAfterGCFlags
 * @bug 8000831
 * @summary Runs an simple application (GarbageProducer) with various
         combinations of -XX:{+|-}Verify{After|Before}GC flags and checks that
         output contain or doesn't contain expected patterns
 * @requires vm.gc != "Z" & vm.gc != "Shenandoah"
 * @modules java.base/jdk.internal.misc
 * @modules java.management
 * @library /test/lib
 * @library /
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver gc.arguments.TestVerifyBeforeAndAfterGCFlags
 */

import java.util.ArrayList;
import java.util.Collections;

import jdk.test.lib.Utils;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.whitebox.WhiteBox;

public class TestVerifyBeforeAndAfterGCFlags {

    // VerifyBeforeGC:[Verifying threads heap tenured eden syms strs zone dict metaspace chunks hand code cache ]
    public static final String VERIFY_BEFORE_GC_PATTERN = "Verifying Before GC";
    // VerifyBeforeGC: VerifyBeforeGC: VerifyBeforeGC:
    public static final String VERIFY_BEFORE_GC_CORRUPTED_PATTERN = "VerifyBeforeGC:(?!\\[Verifying[^]]+\\])";

    // VerifyAfterGC:[Verifying threads heap tenured eden syms strs zone dict metaspace chunks hand code cache ]
    public static final String VERIFY_AFTER_GC_PATTERN = "Verifying After GC";
    // VerifyAfterGC: VerifyAfterGC: VerifyAfterGC:
    public static final String VERIFY_AFTER_GC_CORRUPTED_PATTERN = "VerifyAfterGC:(?!\\[Verifying[^]]+\\])";

    public static void main(String args[]) throws Exception {
        String[] filteredOpts = Utils.getFilteredTestJavaOpts(
                                    new String[] { "-Xlog:gc+verify=debug",
                                                   "-XX:+UseGCLogFileRotation",
                                                   "-XX:-DisplayVMOutput",
                                                   "VerifyBeforeGC",
                                                   "VerifyAfterGC" });
        // Young GC
        testVerifyFlags(false, false, false, filteredOpts);
        testVerifyFlags(true,  true,  false, filteredOpts);
        testVerifyFlags(true,  false, false, filteredOpts);
        testVerifyFlags(false, true,  false, filteredOpts);
        // Full GC
        testVerifyFlags(false, false, true, filteredOpts);
        testVerifyFlags(true,  true,  true, filteredOpts);
        testVerifyFlags(true,  false, true, filteredOpts);
        testVerifyFlags(false, true,  true, filteredOpts);
    }

    public static void testVerifyFlags(boolean verifyBeforeGC,
                                       boolean verifyAfterGC,
                                       boolean doFullGC,
                                       String[] opts) throws Exception {
        ArrayList<String> vmOpts = new ArrayList<>();
        if (opts != null && (opts.length > 0)) {
            Collections.addAll(vmOpts, opts);
        }
        Collections.addAll(vmOpts, new String[] {
                                       "-Xbootclasspath/a:.",
                                       "-XX:+UnlockDiagnosticVMOptions",
                                       "-XX:+WhiteBoxAPI",
                                       "-Xlog:gc+verify=debug",
                                       "-Xmx5m",
                                       "-Xms5m",
                                       "-Xmn3m",
                                       (verifyBeforeGC ? "-XX:+VerifyBeforeGC"
                                                       : "-XX:-VerifyBeforeGC"),
                                       (verifyAfterGC ? "-XX:+VerifyAfterGC"
                                                      : "-XX:-VerifyAfterGC"),
                                       GarbageProducer.class.getName(),
                                       doFullGC ? "t" : "f" });
        OutputAnalyzer analyzer = GCArguments.executeLimitedTestJava(vmOpts);

        analyzer.shouldHaveExitValue(0);
        analyzer.shouldNotMatch(VERIFY_BEFORE_GC_CORRUPTED_PATTERN);
        analyzer.shouldNotMatch(VERIFY_AFTER_GC_CORRUPTED_PATTERN);

        if (verifyBeforeGC) {
            analyzer.shouldMatch(VERIFY_BEFORE_GC_PATTERN);
        } else {
            analyzer.shouldNotMatch(VERIFY_BEFORE_GC_PATTERN);
        }

        if (verifyAfterGC) {
            analyzer.shouldMatch(VERIFY_AFTER_GC_PATTERN);
        } else {
            analyzer.shouldNotMatch(VERIFY_AFTER_GC_PATTERN);
        }
    }

    public static class GarbageProducer {
        static long[][] garbage = new long[10][];

        public static void main(String args[]) {
            WhiteBox wb = WhiteBox.getWhiteBox();

            if (args[0].equals("t")) {
                wb.fullGC();
            } else {
                wb.youngGC();
            }
        }
    }
}
