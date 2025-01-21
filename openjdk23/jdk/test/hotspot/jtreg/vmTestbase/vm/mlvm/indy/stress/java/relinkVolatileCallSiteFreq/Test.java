/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */


/*
 * @test
 *
 * @summary converted from VM Testbase vm/mlvm/indy/stress/java/relinkVolatileCallSiteFreq.
 * VM Testbase keywords: [feature_mlvm, nonconcurrent]
 *
 * @library /vmTestbase
 *          /test/lib
 *
 * @comment build test class and indify classes
 * @build vm.mlvm.indy.stress.java.relinkVolatileCallSiteFreq.Test
 * @run driver vm.mlvm.share.IndifiedClassesBuilder
 *
 * @run main/othervm vm.mlvm.indy.stress.java.relinkVolatileCallSiteFreq.Test
 */

package vm.mlvm.indy.stress.java.relinkVolatileCallSiteFreq;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.VolatileCallSite;

import vm.mlvm.indy.share.INDIFY_RelinkCallSiteFreqTest;
import vm.mlvm.share.MlvmTest;

/**
 * The test creates a volatile call site and relinks it from one thread while calling the current
 * target from the other one. Currently there are 6 targets.
 *
 * The test verifies that target changes in the call site are eventually seen by target calling
 * thread by measuring a frequency of calls for each target and comparing it with theoretical frequency.
 *
 */
public class Test extends INDIFY_RelinkCallSiteFreqTest {

    @Override
    protected CallSite createCallSite(MethodHandle mh) {
        return new VolatileCallSite(mh);
    }

    /**
     * Runs the test.
     * @param args Test arguments
     */
    public static void main(String[] args) {
        MlvmTest.launch(args);
    }
}
