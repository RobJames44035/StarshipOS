/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**
 * @test
 * @bug 8182401
 * @summary Verification error for enclosing instance capture inside super constructor invocation
 * @run main ImplicitEnclosingInstanceTest
 */

import java.util.function.Function;

public class ImplicitEnclosingInstanceTest {

    static String cookie = "deadbeef";

    static Object f(Function<String, Object> f) {
        return f.apply("feed");
    }

    class S {
        S(Object s) {
            cookie += "face";
        }
    }

    class A {
        A(String s) {
            cookie = s;
        }
    }

    class B extends S {
        B() {
            super(f(s->new A(s)));
        }
    }

    public static void main(String[] args) {
        new ImplicitEnclosingInstanceTest().new B();
        if (!cookie.equals("feedface"))
            throw new AssertionError("Incorrect cookie!");
    }
}