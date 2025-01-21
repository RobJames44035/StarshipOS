/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

package jit.graph;

import nsk.share.TestFailure;

import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

class test2 {
    private final int[] MethodID = {Globals.MethodID_Array[1], Globals.MethodID_Array[2]};

    public void CallCallMe(Vector summation, Vector ID, Long functionDepth, Integer staticFunctionDepth)
            throws InvocationTargetException {
        Globals.appendSumToSummationVector(MethodID[1], summation);

        if (CGT.shouldFinish()) {
            return;
        }

        if (Globals.VERBOSE) {
            System.out.println("test2.CallCallMe");
        }

        if ((functionDepth.longValue() <= 0) && (staticFunctionDepth.intValue() <= 0)) {
            return;
        }

        MethodData methodCallStr;
        Long numFcalls;
        Integer staticFcalls;
        if (staticFunctionDepth.intValue() > 0) {
            numFcalls = functionDepth;
            staticFcalls = Integer.valueOf(staticFunctionDepth.intValue() - 1);
            methodCallStr = Globals.returnNextStaticMethod(MethodID[1]);

            Globals.addFunctionIDToVector(methodCallStr.id, ID);
        } else {
            numFcalls = Long.valueOf(functionDepth.longValue() - 1);
            staticFcalls = staticFunctionDepth;
            Globals.addFunctionIDToVector(MethodID[0], ID);
            callMe(summation, ID, numFcalls, staticFcalls);
            return;
        }


        try {
            methodCallStr.nextMethod.invoke(methodCallStr.instance,
                    new Object[]{summation, ID, numFcalls, staticFcalls});
        } catch (IllegalAccessException iax) {
            throw new TestFailure("Illegal Access Exception");
        }
    }

    public void callMe(Vector summation, Vector ID, Long functionDepth, Integer staticFunctionDepth)
            throws InvocationTargetException {
        Globals.appendSumToSummationVector(MethodID[0], summation);

        if (CGT.shouldFinish()) {
            return;
        }

        if (Globals.VERBOSE) {
            System.out.println("test2.callMe");
        }

        if ((functionDepth.longValue() <= 0) && (staticFunctionDepth.intValue() <= 0)) {
            return;
        }

        MethodData methodCallStr;
        Long numFcalls;
        Integer staticFcalls;
        if (staticFunctionDepth.intValue() > 0) {
            numFcalls = functionDepth;
            staticFcalls = Integer.valueOf(staticFunctionDepth.intValue() - 1);
            methodCallStr = Globals.returnNextStaticMethod(MethodID[0]);

        } else {
            numFcalls = Long.valueOf(functionDepth.longValue() - 1);
            staticFcalls = staticFunctionDepth;
            methodCallStr = Globals.nextRandomMethod();
        }

        Globals.addFunctionIDToVector(methodCallStr.id, ID);


        try {
            methodCallStr.nextMethod.invoke(methodCallStr.instance,
                    new Object[]{summation, ID, numFcalls, staticFcalls});
        } catch (IllegalAccessException iax) {
            throw new TestFailure("Illegal Access Exception");
        }

    }
}
