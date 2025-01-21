/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/**
 * @test
 * @bug 6541876
 * @summary  "Enclosing Instance" error new in 1.6
 *
 */

public class T6541876a {
    class X {
        class Y {}
    }

    class A extends X {
        class B extends X.Y {}
    }

    public static void main(String[] args) {
        new T6541876a().new A().new B();
    }
}
