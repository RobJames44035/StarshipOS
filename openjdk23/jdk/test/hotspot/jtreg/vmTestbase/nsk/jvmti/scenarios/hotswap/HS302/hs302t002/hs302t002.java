/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/scenarios/hotswap/HS302/hs302t002.
 * VM Testbase keywords: [quick, jpda, jvmti, noras, redefine, feature_hotswap]
 *
 * @library /vmTestbase
 *          /test/lib
 * @build nsk.jvmti.scenarios.hotswap.HS302.hs302t002.hs302t002
 *
 * @comment compile newclassXX to bin/newclassXX
 * @run driver nsk.share.ExtraClassesBuilder
 *      newclass00
 *
 * @run main/othervm/native
 *      -agentlib:hs302t002=pathToNewByteCode=./bin,-waittime=5,package=nsk,samples=100,mode=compiled
 *      nsk.jvmti.scenarios.hotswap.HS302.hs302t002.hs302t002
 */

package nsk.jvmti.scenarios.hotswap.HS302.hs302t002;
import nsk.share.jvmti.RedefineAgent;

public class hs302t002 extends RedefineAgent {

    public hs302t002(String[] arg) {
        super(arg);
    }


    public static void main(String[] arg) {
        arg = nsk.share.jvmti.JVMTITest.commonInit(arg);

        hs302t002 hsCase = new hs302t002(arg);
        System.exit(hsCase.runAgent());
    }

    public boolean agentMethod() {
        boolean pass=false;
        MyClass cls = new MyClass();
        cls.setName("SOME NAME");
        log.println(" cls.toString() "+cls.toString());
        // Redefine should be attempted and failed.
        if (!cls.toString().equals("Default") &&
                ( redefineAttempted() && !isRedefined())  ) {
            pass =true;
            log.println(" Passed ..");
        } else {
            log.println(" Failed ..");
        }
        return pass;
    }
}
