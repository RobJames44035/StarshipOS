/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

public class Invoker {
    /**
     * Use a method handle to invoke m.
     */
    public static void invoke() {
        MyFunctionalInterface fi = null;
        fi = new MethodSupplier()::<Integer, String, Long>m;
        fi.invokeMethodReference();
    }
    /**
     * Invoke m directly.
     */
    public static void invoke2() {
        MethodSupplier ms = new MethodSupplier();
        ms.m();
    }
}

interface MyFunctionalInterface {
       void invokeMethodReference();
}
