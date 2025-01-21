/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/scenarios/hotswap/HS202/hs202t002.
 * VM Testbase keywords: [quick, jpda, jvmti, onload_only_caps, noras, redefine, feature_hotswap]
 * VM Testbase readme:
 * 1. Enable events Exception, MethodExit.
 * 2. Call a synchronized method which also obtains a monitor.
 * 3. Throw an uncaught exception in the method.
 * 4. Redefine a class within triggered callbacks Exception, MethodExit
 * caused by the uncaught exception. New version contains the changed
 * method.
 * 5. Pop a currently executed frame within callback MethodExit.
 *
 * @library /vmTestbase
 *          /test/lib
 * @build nsk.jvmti.scenarios.hotswap.HS202.hs202t002.hs202t002
 *
 * @comment compile newclassXX to bin/newclassXX
 * @run driver nsk.share.ExtraClassesBuilder
 *      newclass00
 *
 * @run main/othervm/native
 *      -agentlib:hs202t002=pathToNewByteCode=./bin,-waittime=5,package=nsk,samples=100,mode=compiled
 *      nsk.jvmti.scenarios.hotswap.HS202.hs202t002.hs202t002
 */

package nsk.jvmti.scenarios.hotswap.HS202.hs202t002;
import nsk.share.jvmti.RedefineAgent;

public class hs202t002 extends RedefineAgent {

    public hs202t002(String[] arg) {
        super(arg);
    }

    public static void main(String[] arg) {
        arg = nsk.share.jvmti.JVMTITest.commonInit(arg);

        hs202t002 hsCase = new hs202t002(arg);
        System.exit(hsCase.runAgent());
    }

    public boolean agentMethod() {
        int state = 0;
        MyThread mt = new MyThread();
        try {
            mt.start();

            while (!isThreadSuspended(mt)) {
                Thread.yield();
            }

            if (!popThreadFrame(mt)) {
                throw new RuntimeException("error in popframe operation!");
            }

            if (!resumeThread(mt)) {
                throw new RuntimeException("error in resuming thread!");
            }

            mt.join();
            state = mt.getValue();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            final int successState = 111;
            boolean passed = false;
            if (successState == state && isRedefined()) {
                passed = true;
                log.println(" ... Passed state (" + state + ")");
            } else {
                log.println(" ... Failed state (" + state + ")");
            }
            return passed;
        }
    }
    private native boolean popThreadFrame(Thread thread);
    private native boolean resumeThread(Thread thread);
    private native boolean isThreadSuspended(Thread thread);
}
