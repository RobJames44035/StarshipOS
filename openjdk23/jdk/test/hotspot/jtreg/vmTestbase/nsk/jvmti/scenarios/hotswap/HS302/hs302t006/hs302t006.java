/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/scenarios/hotswap/HS302/hs302t006.
 * VM Testbase keywords: [quick, jpda, jvmti, noras, redefine, feature_hotswap]
 *
 * @library /vmTestbase
 *          /test/lib
 * @build nsk.jvmti.scenarios.hotswap.HS302.hs302t006.hs302t006
 *
 * @comment compile newclassXX to bin/newclassXX
 * @run driver nsk.share.ExtraClassesBuilder
 *      newclass00
 *
 * @run main/othervm/native
 *      -agentlib:hs302t006=pathToNewByteCode=./bin,-waittime=5,package=nsk,samples=100,mode=compiled
 *      nsk.jvmti.scenarios.hotswap.HS302.hs302t006.hs302t006
 */

package nsk.jvmti.scenarios.hotswap.HS302.hs302t006;

import nsk.share.jvmti.RedefineAgent;
import java.lang.reflect.*;
import nsk.jvmti.scenarios.hotswap.HS302.hs302t006r.MyClass;
public class hs302t006  extends RedefineAgent {

    public hs302t006(String[] arg) {
        super(arg);
    }

    public static void main(String[] arg) {
        arg = nsk.share.jvmti.JVMTITest.commonInit(arg);

        hs302t006 hsCase = new hs302t006(arg);
        System.exit(hsCase.runAgent());
    }

    public boolean agentMethod() {
        boolean  pass=false;
        MyClass cls = new MyClass();
        Class klass = MyClass.class;
        try {
            Method[] methods = klass.getDeclaredMethods();
            for(Method method : methods) {
                if (method.getName().equals("setName")) {
                    System.out.println(" Modified "+method.getModifiers());
                    if ( (Modifier.PUBLIC &  method.getModifiers())==Modifier.PUBLIC ) {
                        System.out.println("...Public ..");
                        pass = true;
                    }
                }
            }
        }catch(Exception exp) {
            if ( isRedefined() ) {
                pass =true;
                log.println(" Passed ..");
            }
        }
        // If the execption is failed to throw.
        if ( redefineAttempted() && !isRedefined() ) {
            pass = true;
        }
        return pass;
    }
}
