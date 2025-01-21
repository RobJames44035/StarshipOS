/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 * @test
 * @summary Test setting breakpoints on lambda calls
 * @author Staffan Larsen
 *
 * @run build TestScaffold VMConnection TargetListener TargetAdapter
 * @run compile -g LambdaBreakpointTest.java
 * @run driver LambdaBreakpointTest
 */

import java.util.List;

import com.sun.jdi.LocalVariable;
import com.sun.jdi.Location;
import com.sun.jdi.Method;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.StackFrame;
import com.sun.jdi.StringReference;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.StepEvent;

 /********** target program **********/

class LambdaBreakpointTestTarg {
    public static void main(String[] args) {
        test();
    }

    private static void test() {
        Runnable r = () -> {                          // LambdaBreakpointTest::TEST_LINE_1, BKPT_LINES[0]
            String from = "lambda";                   // LambdaBreakpointTest::TEST_LINE_2, BKPT_LINES[2]
            System.out.println("Hello from " + from); // LambdaBreakpointTest::TEST_LINE_3, BKPT_LINES[3]
        };                                            // LambdaBreakpointTest::TEST_LINE_4, BKPT_LINES[4]
        r.run();                                      // LambdaBreakpointTest::TEST_LINE_5, BKPT_LINES[1]
        System.out.println("Goodbye.");               // LambdaBreakpointTest::TEST_LINE_6, BKPT_LINES[5]
    }
}


 /********** test program **********/

public class LambdaBreakpointTest extends TestScaffold {
    private static final int TEST_LINE_1 = 57;
    private static final int TEST_LINE_2 = TEST_LINE_1 + 1;
    private static final int TEST_LINE_3 = TEST_LINE_1 + 2;
    private static final int TEST_LINE_4 = TEST_LINE_1 + 3;
    private static final int TEST_LINE_5 = TEST_LINE_1 + 4;
    private static final int TEST_LINE_6 = TEST_LINE_1 + 5;

    private static final int[] BKPT_LINES = {
        TEST_LINE_1,
        TEST_LINE_5,
        TEST_LINE_2,
        TEST_LINE_3,
        TEST_LINE_4,
        TEST_LINE_6,
    };

    LambdaBreakpointTest (String args[]) {
        super(args);
    }

    public static void main(String[] args)
        throws Exception
    {
        new LambdaBreakpointTest (args).startTests();
    }

    /********** test core **********/

    protected void runTests()
        throws Exception
    {
        startToMain("LambdaBreakpointTestTarg");

        // Put a breakpoint on each location in the order they should happen
        for (int line : BKPT_LINES) {
            System.out.println("Running to line: " + line);
            BreakpointEvent be = resumeTo("LambdaBreakpointTestTarg", line);
            int stoppedAt = be.location().lineNumber();
            System.out.println("Stopped at line: " + stoppedAt);
            if (stoppedAt != line) {
                throw new Exception("Stopped on the wrong line: "
                        + stoppedAt + " != " + line);
            }
        }

        /*
         * resume the target listening for events
         */
        listenUntilVMDisconnect();
    }
}
