/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/scenarios/hotswap/HS302/hs302t004.
 * VM Testbase keywords: [quick, jpda, jvmti, noras, redefine, feature_hotswap]
 *
 * @library /vmTestbase
 *          /test/lib
 * @build nsk.jvmti.scenarios.hotswap.HS302.hs302t004.hs302t004
 *
 * @comment compile newclassXX to bin/newclassXX
 * @run driver nsk.share.ExtraClassesBuilder
 *      newclass00
 *
 * @run main/othervm/native
 *      -agentlib:hs302t004=pathToNewByteCode=./bin,-waittime=5,package=nsk,samples=100,mode=compiled
 *      nsk.jvmti.scenarios.hotswap.HS302.hs302t004.hs302t004
 */

package nsk.jvmti.scenarios.hotswap.HS302.hs302t004;

import nsk.share.jvmti.RedefineAgent;
import java.lang.reflect.*;
import nsk.jvmti.scenarios.hotswap.HS302.hs302t004r.MyClass;
public class hs302t004 extends RedefineAgent {
    public hs302t004(String[] arg) {
        super(arg);
    }

    public static void main(String[] arg) {
        arg = nsk.share.jvmti.JVMTITest.commonInit(arg);

        hs302t004 hsCase = new hs302t004(arg);
        System.exit(hsCase.runAgent());
    }
    // changing from public/ private .
    public boolean agentMethod(){
        boolean pass=false;
        try {
            MyClass cls = new MyClass();
            Class klass = MyClass.class;
            Method[] methods = klass.getDeclaredMethods();
            for(Method method : methods) {
                if (method.getName().equals("setName")) {
                    log.println(" Modified "+method.getModifiers());
                    if ( (Modifier.PRIVATE &  method.getModifiers())==Modifier.PRIVATE ) {
                        log.println("...Private..");
                        pass = true;
                    }
                }
            }
            // If you get an exception due to method not found thata good.
        }catch(Exception exp) {
            if ( isRedefined() ) {
                pass =true;
                log.println(" Passed ..");
            } else {
                log.println(" Failed ..");
            }
            return pass;
        }
        // If the execption is failed to throw.
        if ( redefineAttempted() && !isRedefined() ) {
            pass = true;
        }
        log.println(" PASS = "+pass);
        return pass;
    }
}
