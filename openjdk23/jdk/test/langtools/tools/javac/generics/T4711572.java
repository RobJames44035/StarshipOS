/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4711572
 * @summary generics: problem with type inference for recursive methods
 * @author gafter
 *
 * @compile  T4683314.java
 */

class T4711572 {
    static <T> boolean isOK(T x) {
        return isOK(x);
    }

    static <T> boolean isStillOK(T x) {
        return true && isOK(x);
    }

    static <T> boolean isNoMoreOK(T x) {
        return true && isNoMoreOK(x);
    }

    static <T> boolean isOKAgain(T x) {
        boolean res;
        return true && (res = isOKAgain(x));
    }
}
