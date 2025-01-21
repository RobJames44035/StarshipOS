/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package vm.mlvm.indy.stress.gc.lotsOfCallSites;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.HashSet;

import vm.mlvm.share.Env;

/**
 * Testee class, which records calls to bootstrap, target methods and adds callsite created
 * in the bootstrap to reference queue {@link #objQueue} and references set {@link #references}
 */
public class INDIFY_Testee {
    /**
     * Set to true, when bootstrap method is called
     */
    public static boolean bootstrapCalled = false;
    /**
     * Set to true when target method is called
     */
    public static boolean targetCalled = false;

    /**
     * Should be set by the test before call to {@link #indyWrapper()}
     * A reference to the call site created in bootstrap method is added to this set.
     */
    public static HashSet<PhantomReference<CallSite>> references;

    /**
     * Should be set by the test before call to {@link #indyWrapper()}
     * A reference to the call site created in bootstrap method is added to this queue.
     */
    public static ReferenceQueue<CallSite> objQueue;

    private static MethodType MT_bootstrap() {
        return MethodType.methodType(Object.class, Object.class, Object.class, Object.class);
    }

    private static MethodHandle MH_bootstrap() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findStatic(INDIFY_Testee.class, "bootstrap", MT_bootstrap());
    }

    private static MethodHandle INDY_call;
    private static MethodHandle INDY_call() throws Throwable {
        if (INDY_call != null) {
            return INDY_call;
        }
        CallSite cs = (CallSite) MH_bootstrap ().invokeWithArguments(MethodHandles.lookup(), "gimmeTarget", MT_target());
        return cs.dynamicInvoker();
    }

    /**
     * Calls invokedynamic instruction
     */
    public static void indyWrapper() throws Throwable {
        INDY_call().invokeExact();
    }

    private static Object bootstrap(Object l, Object n, Object t) throws Throwable {
        Env.traceDebug("Bootstrap called");
        bootstrapCalled = true;
        CallSite cs = new ConstantCallSite(MethodHandles.lookup().findStatic(INDIFY_Testee.class, "target", MT_target()));
        references.add(new PhantomReference<CallSite>(cs, objQueue));
        return cs;
    }

    private static MethodType MT_target() {
        return MethodType.methodType(void.class);
    }

    private static void target() {
        Env.traceDebug("Target called");
        targetCalled = true;
    }
}
