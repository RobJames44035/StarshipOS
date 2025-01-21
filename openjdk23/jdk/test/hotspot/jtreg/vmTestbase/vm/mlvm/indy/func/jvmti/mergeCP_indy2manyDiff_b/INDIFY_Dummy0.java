/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package vm.mlvm.indy.func.jvmti.mergeCP_indy2manyDiff_b;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import vm.mlvm.share.MlvmTest;

public class INDIFY_Dummy0 {

    private static MethodType MT_bootstrap() {
        return MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class);
    }

    private static MethodHandle MH_bootstrap() throws NoSuchMethodException, IllegalAccessException  {
        return MethodHandles.lookup().findStatic(
                INDIFY_Dummy0.class,
                "bootstrap",
                MT_bootstrap());
    }

    public static CallSite bootstrap(MethodHandles.Lookup l, String name, MethodType mt) throws Throwable {
        MlvmTest.getLog().display("Original bootstrap(): Lookup " + l + "; method name = " + name + "; method type = " + mt);
        redefineNow();
        throw new RuntimeException("Bootstrap method has not been redefined. Test failed.");
    }

    public static Boolean target(Object o, String s, int i) {
        throw new RuntimeException("Original target method was called. Test failed.");
    }

    public static void redefineNow() {}

    private static MethodHandle INDY_call;
    private static MethodHandle INDY_call() throws Throwable {
        if (INDY_call != null)
            return INDY_call;

        CallSite cs = (CallSite) MH_bootstrap().invokeWithArguments(
                MethodHandles.lookup(),
                "greet",
                MethodType.methodType(Boolean.class, Object.class, String.class, int.class));

        return cs.dynamicInvoker();
    }

    public static boolean invokeTarget() throws Throwable {
        return invokeTarget0();
    }

    private static boolean invokeTarget0() throws Throwable {
        Object o = new Object();
        String s = "Original";
        int i = 456;
        return (Boolean) INDY_call().invokeExact(o, s, i);
    }

    public static boolean isRedefinedClass() {
        return false;
    }
}
