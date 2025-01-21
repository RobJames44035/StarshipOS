/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

package jit.graph;

import nsk.share.TestFailure;

import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

class test1 {
    private final int classID = Globals.MethodID_Array[0];

    public void callMe(Vector summation, Vector ID, Long functionDepth, Integer staticFunctionDepth)
            throws InvocationTargetException {
        Globals.appendSumToSummationVector(classID, summation);

        if (CGT.shouldFinish()) {
            return;
        }

        if (Globals.VERBOSE) {
            System.out.println("test1.callMe");
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
            methodCallStr = Globals.returnNextStaticMethod(classID);
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
