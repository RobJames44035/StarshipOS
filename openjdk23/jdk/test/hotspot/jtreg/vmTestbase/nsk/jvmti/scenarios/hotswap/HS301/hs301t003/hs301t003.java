/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/scenarios/hotswap/HS301/hs301t003.
 * VM Testbase keywords: [quick, jpda, jvmti, noras, redefine, feature_hotswap]
 * VM Testbase readme:
 * Description ::
 *    jvmti->RedefineClasses(... ) enables to change class file on runtime subject few constraints.
 *    This change inside the method is a simple and it causes behavioral changes.
 *    The test verifies, if the change is properly propagated or not. Method calls stacks would change for
 *    mutable to immutable objects. Technically, passing by value and reference will change.
 *    Here, method (DummyMethod())  defined in ./MyClass.java, causes change in state of MyClass's instance.
 *    This class would be redefined to ./newclass00/MyClass.java which doesn't change object state.
 *    The object instance passed to method should become 'call by value', because redefined class is immutable.
 *    The test is expected to pass, if the conversion is effective.
 *
 * @library /vmTestbase
 *          /test/lib
 * @build nsk.jvmti.scenarios.hotswap.HS301.hs301t003.hs301t003
 *
 * @comment compile newclassXX to bin/newclassXX
 * @run driver nsk.share.ExtraClassesBuilder
 *      newclass00
 *
 * @run main/othervm/native
 *      -agentlib:hs301t003=pathToNewByteCode=./bin,-waittime=5,package=nsk,samples=100,mode=compiled
 *      nsk.jvmti.scenarios.hotswap.HS301.hs301t003.hs301t003
 */

package nsk.jvmti.scenarios.hotswap.HS301.hs301t003;
import nsk.share.jvmti.RedefineAgent;

public class hs301t003 extends RedefineAgent {

    public hs301t003(String[] arg) {
        super(arg);
    }


    public static void main(String[] arg) {
        arg = nsk.share.jvmti.JVMTITest.commonInit(arg);

        hs301t003 hsCase = new hs301t003(arg);
        System.exit(hsCase.runAgent());
    }

    public boolean agentMethod() {
        boolean  pass =true;
        try {
            int init_state=1000;
            // create mutableObjectInstnce.
            MyClass mutableObj = new MyClass(init_state);
            // Here is its  mutable object.
            invokeMethodsOnMutable(mutableObj);
            if (mutableObj.getCount() == init_state ) {
                pass = true ;
            } else {
                // no change in method state and
                pass = false;
            }
            // see the state of the object passed to method.
            // omgc
            System.out.println(" clas.getCount After redefine"+mutableObj.getCount());
            if (!pass ) {
                System.out.println(" Error occured, error in redefineing.");
            } else {
                System.out.println(" Successfully redefined.");
            }
        }
        catch(java.lang.VerifyError ve) {
            pass = false;
        }
        System.out.println(" PASS "+pass);
        return pass;
    }

    public void invokeMethodsOnMutable(MyClass mutableObj) {
        // Method invoked once.
        System.out.println("  invokeMethodsOnMutable ::   "+mutableObj.getCount());
        mutableObj = mutableObj.DummyMethod();
        // both the refereence and the state got changed.
        System.out.println("  invokeMethodsOnMutable ::  method invocation  "+mutableObj.getCount());
    }
}
