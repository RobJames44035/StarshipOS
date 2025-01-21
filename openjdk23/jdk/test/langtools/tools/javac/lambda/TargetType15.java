/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  bad target-type inference lead to compiler crash
 * @author  Maurizio Cimadamore
 * @compile TargetType15.java
 */

class TargetType15 {

    interface SAM<T> {
        T foo(T a, T b);
    }

    void m1(SAM<? extends String> f_1) {}
    void m2(SAM<? super String> f_2) {}
    void m3(SAM<?> f_3) {}

    SAM<? extends String> f_1 = (a, b) -> a;
    SAM<? super String> f_2 = (a, b) -> a;
    SAM<?> f_3 = (a, b) -> a;

    {
        m1((a, b) -> a);
        m2((a, b) -> a);
        m3((a, b) -> a);
    }
}
