/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/scenarios/hotswap/HS301/hs301t001.
 * VM Testbase keywords: [quick, jpda, jvmti, noras, redefine, feature_hotswap]
 * VM Testbase readme:
 * Description ::
 *     This testcase uses jvmti->RedefineClasses(... ), to change a static methods
 *     modifier to non-static (changes from a class method to instance method).
 *     Redefine should not allow to do that.
 *     The Test will creates an instance of a class, ./MyClass.java. After few steps,
 *     JvmtiEnv will try to redefine it to ./newclass00/MyClass.java.
 *     Method (DummyMethod) in newclass00/MyClass.java does not have static modifier
 *     where as loaded class (./MyClass.java) has.
 *     Because Redefine, will not accept method's modifiers changes, the test is supposed to fail
 *     to redefine the class.
 *     The testcase is said to pass, if jvmti->RedefineClasses(... ) fails.
 *
 *     The Test will creates an instance of a class (MyClass ,
 *     that makes the class as loaded). After few steps later, JvmtiEnv will
 *     redefine it to ./newclass00/MyClass.java .
 *
 *     One of the MyClass's method's static modifier will be droped.
 *     Because Redefine, will not accept method's modifiers changes,
 *     It will fail to redefine the class.
 *
 * @library /vmTestbase
 *          /test/lib
 * @build nsk.jvmti.scenarios.hotswap.HS301.hs301t001.hs301t001
 *
 * @comment compile newclassXX to bin/newclassXX
 * @run driver nsk.share.ExtraClassesBuilder
 *      newclass00
 *
 * @run main/othervm/native
 *      -agentlib:hs301t001=pathToNewByteCode=./bin,-waittime=5,package=nsk,samples=100,mode=compiled
 *      nsk.jvmti.scenarios.hotswap.HS301.hs301t001.hs301t001
 */

package nsk.jvmti.scenarios.hotswap.HS301.hs301t001;
import nsk.share.jvmti.RedefineAgent;

public class hs301t001 extends RedefineAgent {

    public hs301t001(String[] arg) {
        super(arg);
    }


    public static void main(String[] arg) {
        arg = nsk.share.jvmti.JVMTITest.commonInit(arg);

        hs301t001 hsCase = new hs301t001(arg);
        System.exit(hsCase.runAgent());
    }

    public boolean agentMethod() {
        MyClass cls = new MyClass();
        boolean pass=false;
        if ( !redefine() ) {
            pass=true;
        }
        cls.doThis();
        if (!pass)  {
            log.println(" Error occured, error in redefineing (as expected).");
            log.println(" Case passed.");
        } else {
            log.println(" Successfully redefined, (which is not execpeted).");
            log.println(" Case failed.");
        }
        return pass;
    }

    public native boolean redefine();
}
