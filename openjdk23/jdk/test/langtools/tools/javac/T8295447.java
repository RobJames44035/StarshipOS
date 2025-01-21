/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */
/**
 * @test
 * @bug 8295447
 * @summary NullPointerException with invalid pattern matching construct in constructor call
 * @modules jdk.compiler
 * @compile/fail/ref=T8295447.out -XDrawDiagnostics T8295447.java
 */
public class T8295447 {
    class Foo {
        void m(Object o) {
            if(o instanceof Foo(int x)) {}
        }

        Foo(Object o) {
            m((o instanceof Foo(int x))? 0 : 1);
        }
        void m(int i) { }
    }

    class Base { int i; Base(int j) { i = j; } }
    class Sub extends Base {
        Sub(Object o) { super(o instanceof java.awt.Point(int x, int y)? x + y: 0); }
    }
}
