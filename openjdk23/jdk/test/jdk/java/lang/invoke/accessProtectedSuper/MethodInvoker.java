/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import anotherpkg.MethodSupplierOuter;

public class MethodInvoker extends MethodSupplierOuter.MethodSupplier {
    public static void invoke() throws Exception {
        MethodInvoker ms = new MethodInvoker();
        ms.m();
        ms.myfi().invokeMethodReference();
        MyFunctionalInterface fi = ms::m; // Should not fail with modified bytecodes
        fi.invokeMethodReference();
    }

    MyFunctionalInterface myfi() {
        MyFunctionalInterface fi = this::m; // Should not fail with modified bytecodes
        return fi;
    }
}
