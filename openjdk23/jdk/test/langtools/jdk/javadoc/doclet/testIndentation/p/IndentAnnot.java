/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package p;

public class IndentAnnot {
    public void f1(int a, int b) {}

    public void f2(int a, Object... b) {}

    @Deprecated
    public void f3(int a, int b) {}

    @SafeVarargs
    public void f4(int a, Object... b) {}

    @SafeVarargs
    @Deprecated
    public void f5(int a, Object... b) {}
}
