/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package anotherpkg;

public class MethodSupplierOuter {
    // MethodSupplier is "public" for javac compilation, modified to "protected" for test.
    public static class MethodSupplier {
        protected String m()
        {
            return "protected inherited method";
        }
    }
}
