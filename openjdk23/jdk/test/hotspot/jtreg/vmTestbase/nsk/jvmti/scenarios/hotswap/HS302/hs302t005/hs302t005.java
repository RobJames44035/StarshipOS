/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/scenarios/hotswap/HS302/hs302t005.
 * VM Testbase keywords: [quick, jpda, jvmti, noras, redefine, feature_hotswap]
 *
 * @library /vmTestbase
 *          /test/lib
 * @build nsk.jvmti.scenarios.hotswap.HS302.hs302t005.hs302t005
 *
 * @comment compile newclassXX to bin/newclassXX
 * @run driver nsk.share.ExtraClassesBuilder
 *      newclass00
 *
 * @run main/othervm/native
 *      -agentlib:hs302t005=pathToNewByteCode=./bin,-waittime=5,package=nsk,samples=100,mode=compiled
 *      nsk.jvmti.scenarios.hotswap.HS302.hs302t005.hs302t005
 */

package nsk.jvmti.scenarios.hotswap.HS302.hs302t005;

import java.lang.reflect.*;
import nsk.share.jvmti.RedefineAgent;
import nsk.jvmti.scenarios.hotswap.HS302.hs302t005r.MyClass;
public class hs302t005 extends RedefineAgent {
    public hs302t005(String[] arg) {
        super(arg);
    }

    public static void main(String[] arg) {
        arg = nsk.share.jvmti.JVMTITest.commonInit(arg);

        hs302t005 hsCase = new hs302t005(arg);
        System.exit(hsCase.runAgent());
    }

    public boolean agentMethod() {
        boolean pass=false;
        MyClass cls = new MyClass();
        try {
            Class klass = MyClass.class;
            Method[] methods = klass.getDeclaredMethods();
            for(Method method : methods) {
                if (method.getName().equals("setName")) {
                    log.println(" Modified "+method.getModifiers());
                    // Still its private good.
                    if ( (Modifier.PRIVATE &  method.getModifiers())==Modifier.PRIVATE ) {
                        log.println("...Private..");
                        pass = true;
                    }
                }
            }
        } catch(Exception exp) {
            if ( isRedefined() ) {
                pass =true;
                log.println(" Passed ..");
            }
        }
        // If the execption is failed to throw.
        if ( redefineAttempted() && !isRedefined() ) {
            pass = true;
        }
        log.println(" PASS = "+pass);
        return pass;
    }
}
