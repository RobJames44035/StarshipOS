/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */


/*
 * @test
 *
 * @summary converted from VM Testbase vm/mlvm/meth/stress/jdi/breakpointInCompiledCode.
 * VM Testbase keywords: [feature_mlvm, nonconcurrent, jdk, quarantine]
 * VM Testbase comments: 8208255
 * VM Testbase readme:
 * DESCRIPTION
 *     Execute a MethodHandle 10000 times to trigger Hotspot compilation. Set a debugger breakpoint on MH.
 *     Make few debugger steps, obtaining various information from JVM.
 *
 * @library /vmTestbase
 *          /test/lib
 *
 * @comment build debuggee class
 * @build vm.mlvm.share.jdi.MHDebuggee
 *
 * @comment build test class and indify classes
 * @build vm.mlvm.meth.stress.jdi.breakpointInCompiledCode.Test
 * @run driver vm.mlvm.share.IndifiedClassesBuilder
 *
 * @run main/othervm
 *      vm.mlvm.meth.stress.jdi.breakpointInCompiledCode.Test
 *      -verbose
 *      -arch=${os.family}-${os.simpleArch}
 *      -waittime=5
 *      -debugee.vmkind=java
 *      -transport.address=dynamic
 *      -debugger.debuggeeClass vm.mlvm.share.jdi.MHDebuggee
 *      -debuggee.iterations 2000
 *      -debugee.vmkeys="${test.vm.opts} ${test.java.opts}"
 */

package vm.mlvm.meth.stress.jdi.breakpointInCompiledCode;

import vm.mlvm.share.jdi.ArgumentHandler;
import vm.mlvm.share.jdi.BreakpointInfo;
import vm.mlvm.share.jdi.JDIBreakpointTest;

import java.util.ArrayList;
import java.util.List;

public class Test extends JDIBreakpointTest {
    // invokeMH:S100,invokePlain:S100,mhTarget,plainTarget,stop
    @Override
    protected List<BreakpointInfo> getBreakpoints(String debuggeeClassName) {
        List<BreakpointInfo> result = new ArrayList<>();
        {
            BreakpointInfo info = new BreakpointInfo("invokeMH");
            info.stepsToTrace = 100;
            result.add(info);
        }
        {
            BreakpointInfo info = new BreakpointInfo("invokePlain");
            info.stepsToTrace = 100;
            result.add(info);
        }
        result.add(new BreakpointInfo("mhTarget"));
        result.add(new BreakpointInfo("plainTarget"));
        result.add(new BreakpointInfo("stop"));

        return result;
    }

    public static void main(String[] args) {
        launch(new ArgumentHandler(args));
    }
}
