/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase vm/mlvm/indy/func/jdi/breakpointOtherStratum.
 * VM Testbase keywords: [feature_mlvm, nonconcurrent, quarantine]
 * VM Testbase comments: 8199578
 * VM Testbase readme:
 * DESCRIPTION
 *     Performs debugging of invokedynamic call in vm.mlvm.share.jdi.INDIFY_Debuggee (with added
 *     source debug information) and verifies that JDI reports correct SDE locations.
 *
 * @library /vmTestbase
 *          /test/lib
 *
 * @comment build debuggee class
 * @build vm.mlvm.share.jdi.IndyDebuggee
 *
 * @comment build test class and indify classes
 * @build vm.mlvm.indy.func.jdi.breakpointOtherStratum.Test
 * @run driver vm.mlvm.share.IndifiedClassesBuilder
 *
 * @comment recompile INDIFY_SDE_DebuggeeBase with Stratum annotation processor
 * @clean vm.mlvm.share.jpda.INDIFY_SDE_DebuggeeBase
 * @run driver
 *      vm.mlvm.share.StratumClassesBuilder
 *      vmTestbase/vm/mlvm/share/jpda/INDIFY_SDE_DebuggeeBase.java
 *
 * @run main/othervm
 *      vm.mlvm.indy.func.jdi.breakpointOtherStratum.Test
 *      -verbose
 *      -arch=${os.family}-${os.simpleArch}
 *      -waittime=5
 *      -debugee.vmkind=java
 *      -debugee.vmkeys="-cp ./bin/classes${path.separator}${test.class.path}"
 *      -transport.address=dynamic
 *      -debugger.debuggeeClass vm.mlvm.share.jdi.IndyDebuggee
 */

package vm.mlvm.indy.func.jdi.breakpointOtherStratum;

import vm.mlvm.share.jdi.ArgumentHandler;
import vm.mlvm.share.jdi.BreakpointInfo;
import vm.mlvm.share.jdi.JDIBreakpointTest;
import vm.mlvm.share.jpda.StratumInfo;

import java.util.ArrayList;
import java.util.List;

public class Test extends JDIBreakpointTest {
    @Override
    protected List<BreakpointInfo> getBreakpoints(String debuggeeClassName) {
        List<BreakpointInfo> result = new ArrayList<>();
        // indyWrapper:S8000/Logo=INDIFY_SDE_DebuggeeBase.logo:2
        {
            BreakpointInfo info = new BreakpointInfo("indyWrapper");
            info.stepsToTrace = 8000;
            info.stratumInfo = new StratumInfo("Logo", "INDIFY_SDE_DebuggeeBase.logo", 2);
            result.add(info);
        }
        // bootstrap/Logo=INDIFY_SDE_DebuggeeBase.logo:3
        {
            BreakpointInfo info = new BreakpointInfo("bootstrap");
            info.stratumInfo = new StratumInfo("Logo", "INDIFY_SDE_DebuggeeBase.logo", 3);
            result.add(info);
        }
        // target/Logo=INDIFY_SDE_DebuggeeBase.logo:4
        {
            BreakpointInfo info = new BreakpointInfo("target");
            info.stratumInfo = new StratumInfo("Logo", "INDIFY_SDE_DebuggeeBase.logo", 4);
            result.add(info);
        }
        // stop/Logo=INDIFY_SDE_DebuggeeBase.logo:5
        {
            BreakpointInfo info = new BreakpointInfo("stop");
            info.stratumInfo = new StratumInfo("Logo", "INDIFY_SDE_DebuggeeBase.logo", 5);
            result.add(info);
        }

        return result;
    }

    public static void main(String[] args) {
        launch(new ArgumentHandler(args));
    }
}
