/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package vm.mlvm.indy.func.jvmti.mergeCP_none2indy_b;

import vm.mlvm.share.MlvmTest;

import java.lang.invoke.*;

public class INDIFY_Dummy0 {
    private static MethodType MT_bootstrap() {
        return MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class);
    }

    private static MethodHandle MH_bootstrap() throws NoSuchMethodException, IllegalAccessException   {
        return MethodHandles.lookup().findStatic(
                INDIFY_Dummy0.class,
                "bootstrap",
                MT_bootstrap());
    }

    public static CallSite bootstrap(MethodHandles.Lookup l, String name, MethodType mt) throws Throwable {
        MlvmTest.getLog().display("Original bootstrap(): Lookup " + l + "; method name = " + name + "; method type = " + mt);
        CallSite cs = new ConstantCallSite(l.findStatic(INDIFY_Dummy0.class, "target", mt));
        return cs;
    }

    public static Boolean target(Object o, String s, int i) {
        redefineNow();
        throw new RuntimeException("Original target method was called instead of the redefined one. Test failed.");
    }

    public static void redefineNow() {}

    private static MethodHandle INDY_call;
    private static MethodHandle INDY_call() throws Throwable {
        if (INDY_call != null)
            return INDY_call;

        CallSite cs = (CallSite) MH_bootstrap().invokeWithArguments(
                MethodHandles.lookup(),
                "greet",
                MethodType.methodType(Boolean.class, Object.class, String.class,
                        int.class));

        return cs.dynamicInvoker();
    }
    public static boolean invokeTarget() throws Throwable {
        return invokeTarget0();
    }

    private static boolean invokeTarget0() throws Throwable {
        Object o = new Object();
        String s = "Original";
        int i = 456;
        return target(o, s, i);
    }

    public static boolean isRedefinedClass() {
        return false;
    }
}
