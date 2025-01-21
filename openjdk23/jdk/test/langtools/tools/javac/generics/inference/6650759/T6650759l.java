/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug     6650759
 * @summary Inference of formal type parameter (unused in formal parameters) is not performed
 * @compile T6650759l.java
 */

public class T6650759l {

    public static interface A<X> {}

    public static class B implements A<Integer> {}

    public static <X extends A<Y>, Y> Y m1(X x) {
        return null;
    }

    public static void m2(Integer i) {}

    public static void test(B b) {
        m2(m1(b));
    }
}
