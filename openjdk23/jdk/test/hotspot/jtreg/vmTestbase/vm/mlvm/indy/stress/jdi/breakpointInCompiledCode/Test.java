/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase vm/mlvm/indy/stress/jdi/breakpointInCompiledCode.
 * VM Testbase keywords: [feature_mlvm, nonconcurrent, fds, jdk]
 * VM Testbase readme:
 * DESCRIPTION
 *     Execute an invokedynamic instruction 10000 times to trigger Hotspot compilation.
 *     Set a debugger breakpoint to invokedynamic instruction.
 *     Make few debugger steps, obtaining various information from JVM
 *
 * @library /vmTestbase
 *          /test/lib
 *
 * @comment build debuggee class
 * @build vm.mlvm.share.jdi.IndyDebuggee
 *
 * @comment build test class and indify classes
 * @build vm.mlvm.indy.stress.jdi.breakpointInCompiledCode.Test
 * @run driver vm.mlvm.share.IndifiedClassesBuilder
 *
 * @run main/othervm
 *      vm.mlvm.indy.stress.jdi.breakpointInCompiledCode.Test
 *      -verbose
 *      -arch=${os.family}-${os.simpleArch}
 *      -waittime=5
 *      -debugee.vmkind=java
 *      -transport.address=dynamic
 *      -debugger.debuggeeClass vm.mlvm.share.jdi.IndyDebuggee
 *      -debuggee.iterations 20000
 *      -debugee.vmkeys="${test.vm.opts} ${test.java.opts}"
 */

package vm.mlvm.indy.stress.jdi.breakpointInCompiledCode;

import vm.mlvm.share.jdi.ArgumentHandler;
import vm.mlvm.share.jdi.BreakpointInfo;
import vm.mlvm.share.jdi.JDIBreakpointTest;

import java.util.ArrayList;
import java.util.List;

public class Test extends JDIBreakpointTest {
    // bootstrap,runDebuggee=>(indyWrapper:S5000,~target,stop)
    @Override
    protected List<BreakpointInfo> getBreakpoints(String debuggeeClassName) {
        List<BreakpointInfo> result = new ArrayList<>();
        result.add(new BreakpointInfo("bootstrap"));
        {
            BreakpointInfo info = new BreakpointInfo("runDebuggee");
            // =>(indyWrapper:S5000,~target,stop)
            List<BreakpointInfo> subBreakpoints = new ArrayList<>();
            {
                BreakpointInfo sub = new BreakpointInfo("indyWrapper", true);
                sub.stepsToTrace = 5000;
                subBreakpoints.add(sub);
            }
            {
                BreakpointInfo sub = new BreakpointInfo("target", true);
                sub.type = BreakpointInfo.Type.IMPLICIT;
                subBreakpoints.add(sub);
            }
            subBreakpoints.add(new BreakpointInfo("stop", true));

            info.subBreakpoints = subBreakpoints;
            result.add(info);
        }

        return result;
    }

    public static void main(String[] args) {
        launch(new ArgumentHandler(args));
    }
}
