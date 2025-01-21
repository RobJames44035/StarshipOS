/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */


/*
 * @test
 *
 * @summary converted from VM Testbase vm/mlvm/indy/stress/java/relinkMutableCallSite.
 * VM Testbase keywords: [feature_mlvm, nonconcurrent, quarantine]
 * VM Testbase comments: 8079664
 * VM Testbase readme:
 * DESCRIPTION
 *    The test creates a mutable call site and relinks it from one thread while calling target from
 *    the other one.
 *    The test verifies that target changes in the call site are eventually seen by target calling
 *    thread by comparing the number of just called target with "golden" one, supplied by target
 *    relinking thread.
 *    For internal synchronization between the threads the test uses a non-volatile variable
 *    without any synchronized() statements or java.util.concurrent classes.
 *    The test artificially loses synchronization sometimes to verify that test logic is correct.
 *
 * @library /vmTestbase
 *          /test/lib
 *
 * @comment build test class and indify classes
 * @build vm.mlvm.indy.stress.java.relinkMutableCallSite.Test
 * @run driver vm.mlvm.share.IndifiedClassesBuilder
 *
 * @run main/othervm
 *      vm.mlvm.indy.stress.java.relinkMutableCallSite.Test
 *      -stressIterationsFactor 100000
 */

package vm.mlvm.indy.stress.java.relinkMutableCallSite;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MutableCallSite;

import vm.mlvm.indy.share.INDIFY_RelinkCallSiteTest;
import vm.mlvm.share.MlvmTest;

public class Test extends INDIFY_RelinkCallSiteTest {

    @Override
    protected CallSite createCallSite(MethodHandle mh) {
        return new MutableCallSite(mh);
    }

    public static void main(String[] args) { MlvmTest.launch(args); }
}
