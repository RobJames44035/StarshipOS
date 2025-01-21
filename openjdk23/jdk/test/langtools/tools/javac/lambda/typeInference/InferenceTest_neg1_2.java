/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class InferenceTest_neg1_2 {

    public static void meth() {
        InferenceTest_neg1_2 test = new InferenceTest_neg1_2();
        test.method(n -> null); //method 1-5 all match
        test.method(n -> "a"); //method 2, 4 match
        test.method(n -> 0); //method 1, 3, 5 match
    }

    void method(SAM1 s) { //method 1
        Integer i = s.foo("a");
    }

    void method(SAM2 s) { //method 2
        String str = s.foo(0);
    }

    void method(SAM3<Integer> s) { //method 3
        Integer i = s.get(0);
    }

    void method(SAM4<Double, String> s) { //method 4
        String str = s.get(0.0);
    }

    void method(SAM5<Integer> s) { //method 5
        Integer i = s.get(0.0);
    }

    interface SAM1 {
        Integer foo(String a);
    }

    interface SAM2 {
        String foo(Integer a);
    }

    interface SAM3<T> {
        T get(T t);
    }

    interface SAM4<T, V> {
        V get(T t);
    }

    interface SAM5<T> {
        T get(Double i);
    }
}
