/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8159680
 * @summary Inference failure with unchecked subtyping and arrays
 * @compile T8159680.java
 */

class T8159680 {

    static class Condition<T> {}

    @SafeVarargs
    static <T> Condition<T> allOf(Condition<? super T>... conditions) {
        return null;
    }

    @SafeVarargs
    static void test(Condition<? super Number>... conditions) {
        allOf(conditions);
    }
}
