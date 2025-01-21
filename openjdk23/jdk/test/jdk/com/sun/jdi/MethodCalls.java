/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

import java.lang.reflect.*;

/*
 * Debuggee which exercises various types method calls
 */

class MethodCalls {

    public static void main(String args[]) throws Exception {
        (new MethodCalls()).go();
    }

    static void staticCaller(MethodCalls mc) throws Exception {
        System.out.println("Called staticCaller");
        staticCallee();
        mc.instanceCallee();

        /*
         * Invocation by reflection. This also exercises native method calls
         * since Method.invoke is a native method.
         */
        Method m = MethodCalls.class.getDeclaredMethod("staticCallee", new Class[0]);
        m.invoke(mc, new Object[0]);
    }

    void instanceCaller() throws Exception {
        System.out.println("Called instanceCaller");
        staticCallee();
        instanceCallee();

        /*
         * Invocation by reflection. This also exercises native method calls
         * since Method.invoke is a native method.
         */
        Method m = getClass().getDeclaredMethod("instanceCallee", new Class[0]);
        m.invoke(this, new Object[0]);
    }

    static void staticCallee() {
        System.out.println("Called staticCallee");
    }

    void instanceCallee() {
        System.out.println("Called instanceCallee");
    }

    void go() throws Exception {
        instanceCaller();
        staticCaller(this);
    }
}
