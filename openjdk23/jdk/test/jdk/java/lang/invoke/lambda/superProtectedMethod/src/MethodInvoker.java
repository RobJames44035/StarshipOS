/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import anotherpkg.MethodSupplierOuter;

public class MethodInvoker extends MethodSupplierOuter.MethodSupplier  {
    public static String invoke() {
        MyFunctionalInterface fi = new MethodInvoker()::m;
        return fi.invokeMethodReference();
    }
}
