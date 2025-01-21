/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * @test
 * @summary Test stepping through lambdas
 * @author Staffan Larsen
 *
 * @run build TestScaffold VMConnection TargetListener TargetAdapter
 * @run compile -g LambdaStepTest.java
 * @run driver LambdaStepTest
 */
import com.sun.jdi.LocalVariable;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.StackFrame;
import com.sun.jdi.StringReference;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.StepEvent;

 /********** target program **********/

interface DefaultTest {
    default void defaultMethod() {
        String from = "default";
        System.out.println("Hello from " + from);
    }
}
class LambdaStepTestTarg implements DefaultTest {
    private void test() {
        String from = "test";
        System.out.println("Hello from " + from);
    }
    private static void instanceTest() {
        LambdaStepTestTarg l = new LambdaStepTestTarg();
        l.test();
    }
    private static void lambdaTest() {
        Runnable r = () -> {
            String from = "lambda";
            System.out.println("Hello from " + from);
        };
        r.run();
    }
    private static void defaultTest() {
        LambdaStepTestTarg l = new LambdaStepTestTarg();
        l.defaultMethod();
    }
    public static void main(String[] args) {
        instanceTest();
        lambdaTest();
        defaultTest();
        System.out.println("Goodbye from LambdaStepTestTarg!");
    }
}


 /********** test program **********/

public class LambdaStepTest extends TestScaffold {
    LambdaStepTest (String args[]) {
        super(args);
    }

    public static void main(String[] args)
        throws Exception
    {
        new LambdaStepTest (args).startTests();
    }

    /********** test core **********/

    protected void runTests()
        throws Exception
    {
        // ## Normal instance method

        BreakpointEvent bpe = startTo("LambdaStepTestTarg", "instanceTest", "()V");
        ThreadReference thread = bpe.thread();

        // step over allocation
        StepEvent se = stepOverLine(thread);
        System.out.println(se.thread().frame(0));

        // step into test();
        se = stepIntoLine(thread);
        System.out.println(se.thread().frame(0));

        // step over variable initialization
        se = stepOverLine(thread);
        System.out.println(se.thread().frame(0));

        // get value of variable "from"
        StackFrame frame = se.thread().frame(0);
        LocalVariable lv = frame.visibleVariableByName("from");
        System.out.println(lv);
        StringReference sr = (StringReference) frame.getValue(lv);
        if (!sr.value().equals("test")) {
            throw new Exception("Unexpected variable value in instanceTest: "+sr.value());
        }


        // ## Lambda method

        bpe = resumeTo("LambdaStepTestTarg", "lambdaTest", "()V");
        thread = bpe.thread();

        // step over allocation
        se = stepOverLine(thread);
        System.out.println(se.thread().frame(0));

        // step into run() of the lambda
        se = stepIntoLine(thread);
        System.out.println(se.thread().frame(0));

        // step over variable initialization
        se = stepOverLine(thread);
        System.out.println(se.thread().frame(0));

        // get value of variable "from"
        frame = se.thread().frame(0);
        lv = frame.visibleVariableByName("from");
        System.out.println(lv);
        sr = (StringReference) frame.getValue(lv);
        if (!sr.value().equals("lambda")) {
            throw new Exception("Unexpected variable value in lambdaTest: "+sr.value());
        }


        // ## Default method

        bpe = resumeTo("LambdaStepTestTarg", "defaultTest", "()V");
        thread = bpe.thread();

        // step over allocation
        se = stepOverLine(thread);
        System.out.println(se.thread().frame(0));

        // step into defaultMethod()
        se = stepIntoLine(thread);
        System.out.println(se.thread().frame(0));

        // step over variable initialization
        se = stepOverLine(thread);
        System.out.println(se.thread().frame(0));

        // get value of variable "from"
        frame = se.thread().frame(0);
        lv = frame.visibleVariableByName("from");
        System.out.println(lv);
        sr = (StringReference) frame.getValue(lv);
        if (!sr.value().equals("default")) {
            throw new Exception("Unexpected variable value in lambdaTest: "+sr.value());
        }


        /*
         * resume the target listening for events
         */
        listenUntilVMDisconnect();

    }
}
