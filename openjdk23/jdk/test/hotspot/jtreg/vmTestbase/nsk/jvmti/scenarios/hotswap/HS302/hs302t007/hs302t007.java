/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/scenarios/hotswap/HS302/hs302t007.
 * VM Testbase keywords: [quick, jpda, jvmti, noras, redefine, feature_hotswap]
 *
 * @library /vmTestbase
 *          /test/lib
 * @build nsk.jvmti.scenarios.hotswap.HS302.hs302t007.hs302t007
 *
 * @comment compile newclassXX to bin/newclassXX
 * @run driver nsk.share.ExtraClassesBuilder
 *      newclass00
 *
 * @run main/othervm/native
 *      -agentlib:hs302t007=pathToNewByteCode=./bin,-waittime=5,package=nsk,samples=100,mode=compiled
 *      nsk.jvmti.scenarios.hotswap.HS302.hs302t007.hs302t007
 */

package nsk.jvmti.scenarios.hotswap.HS302.hs302t007;

import java.lang.reflect.*;
import nsk.share.jvmti.RedefineAgent;
import nsk.jvmti.scenarios.hotswap.HS302.hs302t007r.MyClass;
public class hs302t007 extends RedefineAgent {
    public hs302t007(String[] arg) {
        super(arg);
    }


    public static void main(String[] arg) {
        arg = nsk.share.jvmti.JVMTITest.commonInit(arg);

        hs302t007 hsCase = new hs302t007(arg);
        System.exit(hsCase.runAgent());
    }

    public boolean agentMethod() {
        boolean  pass= false;
        try {
            MyClass cls = new MyClass();
            Class klass = MyClass.class;
            Method[] methods = klass.getDeclaredMethods();
            for(Method method : methods) {
                if (method.getName().equals("setName")) {
                    log.println(" Modified "+method.getModifiers());
                    if ( (Modifier.SYNCHRONIZED &  method.getModifiers())==Modifier.SYNCHRONIZED ) {
                        log.println("...Synchronized..");
                        pass = true;
                    }
                }
            }
        }catch(Exception exp) {
            if ( redefineAttempted() && !isRedefined()) {
                pass =true;
                log.println(" Passed ..");
            } else {
                log.println(" Failed ..");
            }
        }
        return pass;
    }
}
