/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import jdk.test.lib.dcmd.PidJcmdExecutor;
import jdk.test.lib.process.OutputAnalyzer;

import java.util.Iterator;

/*
 * @test CompilerMemoryStatisticTest
 * @summary Test Compiler.memory
 * @requires vm.compiler1.enabled
 * @requires vm.compiler2.enabled
 *
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run main/othervm -XX:CompileCommand=memstat,*.* CompilerMemoryStatisticTest
 */

public class CompilerMemoryStatisticTest {

    public static void main(String args[]) throws Exception {
        PidJcmdExecutor executor = new PidJcmdExecutor();
        OutputAnalyzer out = executor.execute("Compiler.memory");
        out.shouldHaveExitValue(0);

        // Looks like this:
        // total     Others    RA        HA        NA        result  #nodes  limit   time    type  #rc thread             method
        // 1898600   853176    750872    0         294552    ok      934     -       1.501   c2    1   0x00007f4ec00d3330 java/lang/String::replace((CC)Ljava/lang/String;)
        out.shouldMatch("total.*method");
        out.shouldMatch("\\d+ +(\\d+ +){4}\\S+ +\\d+.*java.*\\(.*\\)");
    }
}
