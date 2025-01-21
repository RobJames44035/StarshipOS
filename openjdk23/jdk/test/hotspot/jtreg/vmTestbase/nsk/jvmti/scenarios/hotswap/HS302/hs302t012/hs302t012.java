/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/scenarios/hotswap/HS302/hs302t012.
 * VM Testbase keywords: [quick, jpda, jvmti, noras, redefine, feature_hotswap]
 *
 * @library /vmTestbase
 *          /test/lib
 * @build nsk.jvmti.scenarios.hotswap.HS302.hs302t012.hs302t012
 *
 * @comment compile newclassXX to bin/newclassXX
 * @run driver nsk.share.ExtraClassesBuilder
 *      newclass00
 *
 * @run main/othervm/native
 *      -agentlib:hs302t012=pathToNewByteCode=./bin,-waittime=5,package=nsk,samples=100,mode=compiled
 *      nsk.jvmti.scenarios.hotswap.HS302.hs302t012.hs302t012
 */

package nsk.jvmti.scenarios.hotswap.HS302.hs302t012;

import java.lang.reflect.*;
import nsk.jvmti.scenarios.hotswap.HS302.hs302t012r.MyClass;
import nsk.share.jvmti.RedefineAgent;

public class hs302t012 extends RedefineAgent {
    public hs302t012(String[] arg) {
        super(arg);
    }

    public static void main(String[] arg) {
        arg = nsk.share.jvmti.JVMTITest.commonInit(arg);

        hs302t012 hsCase = new hs302t012(arg);
        System.exit(hsCase.runAgent());
    }
    // changing from public/ private .
    public boolean agentMethod(){
        boolean pass=false;
        try {
            String name = "MYNAME";
            MyClass cls = new MyClass();
            Class klass = MyClass.class;
            Method[] methods = klass.getDeclaredMethods();
            for(Method method : methods) {
                if (method.getName().equals("setName")) {
                    if ((method.getModifiers() | Modifier.PROTECTED) == Modifier.PROTECTED) {
                        pass=true;
                        System.out.println(".... Passed..");
                    } else {
                        System.out.println(" ... Failed..");
                    }
                }
            }
        }catch(Exception exp) {
            exp.printStackTrace();
            log.println(" Exception "+exp.getMessage());
        }
        if ( redefineAttempted() && !isRedefined() ) {
            pass = true;
        }
        log.println(" PASS = "+pass);
        return pass;
    }
}
