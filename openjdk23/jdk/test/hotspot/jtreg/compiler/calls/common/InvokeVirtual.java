/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package compiler.calls.common;

import jdk.test.lib.Asserts;

/**
 * A test class checking InvokeVirtual instruction
 */

public class InvokeVirtual extends CallsBase {
    private static final Object LOCK = new Object();

    public static void main(String args[]) {
        new InvokeVirtual().runTest(args);
    }

    /**
     * A native caller method, assumed to called "callee"/"calleeNative"
     */
    @Override
    public native void callerNative();

    /**
     * A caller method, assumed to called "callee"/"calleeNative"
     */
    @Override
    public void caller() {
        if (nativeCallee) {
            Asserts.assertTrue(calleeNative(1, 2L, 3.0f, 4.0d, "5"), CALL_ERR_MSG);
        } else {
            Asserts.assertTrue(callee(1, 2L, 3.0f, 4.0d, "5"), CALL_ERR_MSG);
        }
    }

    /**
     * A callee method, assumed to be called by "caller"/"callerNative"
     */
    public boolean callee(int param1, long param2, float param3, double param4,
            String param5) {
        calleeVisited = true;
        CallsBase.checkValues(param1, param2, param3, param4, param5);
        return true;
    }

    /**
     * A native callee method, assumed to be called by "caller"/"callerNative"
     */
    public native boolean calleeNative(int param1, long param2, float param3,
            double param4, String param5);

    /**
     * Returns object to lock execution on
     * @return lock object
     */
    @Override
    protected Object getLockObject() {
        return LOCK;
    }
}
