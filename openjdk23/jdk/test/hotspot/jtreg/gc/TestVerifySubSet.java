/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package gc;

/* @test TestVerifySubSet.java
 * @bug 8072725
 * @summary Test VerifySubSet option
 * @comment ZGC can't use the generic Universe::verify because
 *          there's no guarantee that we will ever have a stable
 *          snapshot where all roots can be verified.
 * @requires vm.gc != "Z"
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 * @run main gc.TestVerifySubSet
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import java.util.ArrayList;
import java.util.Collections;
import jdk.test.lib.Utils;

class TestVerifySubSetRunSystemGC {
    public static void main(String args[]) throws Exception {
        System.gc();
    }
}

public class TestVerifySubSet {

    private static OutputAnalyzer runTest(String subset) throws Exception {
        ArrayList<String> vmOpts = new ArrayList<>();

        Collections.addAll(vmOpts, Utils.getFilteredTestJavaOpts("-Xlog.*"));
        Collections.addAll(vmOpts, new String[] {"-XX:+UnlockDiagnosticVMOptions",
                                                 "-XX:+VerifyBeforeGC",
                                                 "-XX:+VerifyAfterGC",
                                                 "-Xlog:gc+verify=debug",
                                                 "-XX:VerifySubSet="+subset,
                                                 TestVerifySubSetRunSystemGC.class.getName()});
        OutputAnalyzer output = ProcessTools.executeLimitedTestJava(vmOpts);

        System.out.println("Output:\n" + output.getOutput());
        return output;
    }

    public static void main(String args[]) throws Exception {

        OutputAnalyzer output;

        output = runTest("heap, threads, codecache, metaspace");
        output.shouldContain("Heap");
        output.shouldContain("Threads");
        output.shouldContain("CodeCache");
        output.shouldContain("MetaspaceUtils");
        output.shouldNotContain("SymbolTable");
        output.shouldNotContain("StringTable");
        output.shouldNotContain("SystemDictionary");
        output.shouldNotContain("CodeCache Oops");
        output.shouldHaveExitValue(0);

        output = runTest("hello, threads, codecache, metaspace");
        output.shouldContain("memory sub-system is unknown, please correct it");
        output.shouldNotContain("Threads");
        output.shouldNotContain("CodeCache");
        output.shouldNotContain("MetaspaceUtils");
        output.shouldHaveExitValue(1);
    }
}
