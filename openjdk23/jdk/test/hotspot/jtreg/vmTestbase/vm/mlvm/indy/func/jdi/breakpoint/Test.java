/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase vm/mlvm/indy/func/jdi/breakpoint.
 * VM Testbase keywords: [feature_mlvm, nonconcurrent, quarantine]
 * VM Testbase comments: 8199578
 * VM Testbase readme:
 * DESCRIPTION
 *     Using JDI set a debugger breakpoint on invokedynamic instruction.
 *     Go few steps, obtaining various information from JVM.
 *
 * @library /vmTestbase
 *          /test/lib
 *
 * @comment build debuggee class
 * @build vm.mlvm.share.jdi.IndyDebuggee
 *
 * @comment build test class and indify classes
 * @build vm.mlvm.indy.func.jdi.breakpoint.Test
 * @run driver vm.mlvm.share.IndifiedClassesBuilder
 *
 * @run main/othervm
 *      vm.mlvm.indy.func.jdi.breakpoint.Test
 *      -verbose
 *      -arch=${os.family}-${os.simpleArch}
 *      -waittime=5
 *      -debugee.vmkind=java
 *      -transport.address=dynamic
 *      -debugger.debuggeeClass vm.mlvm.share.jdi.IndyDebuggee
 */

package vm.mlvm.indy.func.jdi.breakpoint;

import vm.mlvm.share.jdi.ArgumentHandler;
import vm.mlvm.share.jdi.BreakpointInfo;
import vm.mlvm.share.jdi.JDIBreakpointTest;

import java.util.ArrayList;
import java.util.List;

public class Test extends JDIBreakpointTest {
    // indyWrapper:S8000,bootstrap,target,stop
    @Override
    protected List<BreakpointInfo> getBreakpoints(String debuggeeClassName) {
        List<BreakpointInfo> result = new ArrayList<>();
        {
            BreakpointInfo info = new BreakpointInfo("indyWrapper");
            info.stepsToTrace = 8000;
            result.add(info);
        }
        result.add(new BreakpointInfo("bootstrap"));
        result.add(new BreakpointInfo("target"));
        result.add(new BreakpointInfo("stop"));

        return result;
    }

    public static void main(String[] args) {
        launch(new ArgumentHandler(args));
    }
}
