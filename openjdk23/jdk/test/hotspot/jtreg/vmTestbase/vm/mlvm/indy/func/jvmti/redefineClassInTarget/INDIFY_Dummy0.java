/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.indy.func.jvmti.redefineClassInTarget;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import vm.mlvm.share.MlvmTest;

public class INDIFY_Dummy0 {

    private static MethodType MT_bootstrap() {
        return MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class);
    }

    private static MethodHandle MH_bootstrap() throws NoSuchMethodException, IllegalAccessException {
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
        MlvmTest.getLog().display("Original target called! Object = " + o + "; string = " + s + "; int = " + i);
        MlvmTest.getLog().display("The rest of methods are from " + (isRedefinedClass() ? "redefined" : "original") + " class");
        redefineNow();
        return false;
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
        //return (boolean) InvokeDynamic.greet(new Object(), "Redefined", 456);
        Object o = new Object();
        String s = "Original";
        int i = 456;
        return (Boolean) INDY_call().invokeExact(o, s, i);
    }

    public static boolean isRedefinedClass() {
        return false;
    }
}
