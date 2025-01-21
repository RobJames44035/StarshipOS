/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8023389
 * @summary Javac fails to infer type for lambda used with intersection type and wildcards
 * @compile T8023389.java
 */
public class T8023389 {

    static class U1 {}
    static class X1 extends U1 {}

    interface I { }

    interface SAM<T> {
        void m(T t);
    }

    /* Strictly speaking only the second of the following declarations provokes the bug.
     * But the first line is also a useful test case.
     */
    SAM<? extends U1> sam1 = (SAM<? extends U1>) (X1 x) -> { };
    SAM<? extends U1> sam2 = (SAM<? extends U1> & I) (X1 x) -> { };
}
