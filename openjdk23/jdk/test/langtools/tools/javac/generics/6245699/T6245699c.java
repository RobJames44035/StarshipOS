/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     6245699
 * @summary Missing bridge for final method (gives AbstractMethodError at runtime)
 * @author  Peter von der Ah\u00e9
 */

public class T6245699c {
    public static void main(String[] args) {
        Interface x = new Derived();
        x.method("blah"); // throws AbstractMethodError
    }

    static interface Interface {
        void method(String arg);
    }

    static class Base<T> {
        public final void method(T arg) {}
    }

    static class Derived extends Base<String> implements Interface {}
}
