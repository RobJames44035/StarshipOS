/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.util.Iterator;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.dcmd.PidJcmdExecutor;

/*
 * @test CodeHeapAnalyticsMethodNames
 * @summary Test Compiler.CodeHeap_Analytics output has qualified method names
 * in the 'METHOD NAMES' section.
 * @bug 8275729
 * @requires vm.flagless
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver CodeHeapAnalyticsMethodNames
 */

public class CodeHeapAnalyticsMethodNames {

    public static void main(String args[]) throws Exception {
        PidJcmdExecutor executor = new PidJcmdExecutor();
        OutputAnalyzer out = executor.execute("Compiler.CodeHeap_Analytics");
        out.shouldHaveExitValue(0);
        Iterator<String> iter = out.asLines().listIterator();
        boolean methodNamesSectionFound = false;
        while (iter.hasNext()) {
            String line = iter.next();
            if (line.contains("M E T H O D   N A M E S")) {
                methodNamesSectionFound = true;
                break;
            }
        }
        boolean nMethodFound = false;
        while (iter.hasNext()) {
            String line = iter.next();
            if (line.startsWith("0x") && line.contains("nMethod")) {
                nMethodFound = true;
                if (line.contains("java.lang.invoke.MethodHandle")) {
                    return;
                }
            }
        }
        if (methodNamesSectionFound && nMethodFound) {
            throw new RuntimeException("No java.lang.invoke.MethodHandle found.");
        }
    }
}
