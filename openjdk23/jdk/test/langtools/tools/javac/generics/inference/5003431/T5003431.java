/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     5003431
 * @summary java.lang.Object cannot be dereferenced
 * @compile T5003431.java
 */

public class T5003431 {
    static class SomeType<T> { T t = null; }

    static <T> T nil() { return (new SomeType<T>()).t; }

    public static void test() {
        nil().getClass();
    }
}
