/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.IsMethodObsolete;

public class isobsolete001r {

    public static int testedStaticMethod(int n, isobsolete001r obj) {
        return obj.testedInstanceMethod(n);
    }

    public int testedInstanceMethod(int n) {
        isobsolete001.running();
        return n;
    }
}
