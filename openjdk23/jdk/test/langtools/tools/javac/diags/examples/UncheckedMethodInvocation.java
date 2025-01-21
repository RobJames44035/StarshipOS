/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.warn.unchecked.meth.invocation.applied
// key: compiler.warn.prob.found.req
// key: compiler.misc.unchecked.assign
// options: -Xlint:unchecked

class UncheckedMethodInvocation {
    class X<T> {}

    public <T> void m(X<T> x, T t) {}

    public void test() {
        m(new X<X<Integer>>(), new X());
    }
}
