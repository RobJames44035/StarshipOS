/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8055963 8062373
 * @summary Inference failure with nested invocation
 */
public class T8055963 {

    static class C<T> {}

    <T> T choose(T first, T second) { return null; }

    void test() {
        C<String> cs = choose(new C<String>(), new C<>());
    }

    public static void main(String [] args) {
      new T8055963().test();
    }
}
