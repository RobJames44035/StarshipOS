/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6999067 7010194
 * @summary cast for invokeExact call gets redundant cast to <type> warnings
 * @author mcimadamore
 *
 * @compile -Werror -Xlint:cast XlintWarn.java
 */

import java.lang.invoke.*;

class XlintWarn {
    void test(MethodHandle mh) throws Throwable {
        int i1 = (int)mh.invokeExact();
        int i2 = (int)mh.invoke();
        int i3 = (int)mh.invokeWithArguments();
    }

    void test2(MethodHandle mh) throws Throwable {
        int i1 = (int)(mh.invokeExact());
        int i2 = (int)(mh.invoke());
        int i3 = (int)(mh.invokeWithArguments());
    }

    void test3(MethodHandle mh) throws Throwable {
        int i1 = (int)((mh.invokeExact()));
        int i2 = (int)((mh.invoke()));
        int i3 = (int)((mh.invokeWithArguments()));
    }
}
