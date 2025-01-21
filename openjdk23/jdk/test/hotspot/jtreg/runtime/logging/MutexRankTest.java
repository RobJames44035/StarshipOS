/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8294759
 * @summary Verify mutex rank logging works
 * @requires vm.flagless
 * @library /test/lib
 * @run driver MutexRankTest
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.Platform;

public class MutexRankTest {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Xlog:vmmutex",
                                                                             "-version");
        OutputAnalyzer oa = new OutputAnalyzer(pb.start());
        oa.shouldContain("VM Mutex/Monitor ranks:");
        if (Platform.isDebugBuild()) {
            oa.shouldContain("Rank \"safepoint\"");
            oa.shouldContain("Heap_lock");
        } else {
            oa.shouldContain("Only known in debug builds");
        }
        oa.shouldHaveExitValue(0);
    }
}
