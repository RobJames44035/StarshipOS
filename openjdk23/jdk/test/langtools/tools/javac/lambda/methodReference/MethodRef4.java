/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/**
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *   Test constructor reference
 * @compile MethodRef4.java
 * @run main MethodRef4
 */

public class MethodRef4 {

    static interface A {Fee<String> m();}

    static interface B {Fee<String> m(String s);}

    static interface C {Object m();}

    static class Fee<T> {

        private T t;

        public Fee() {
            System.out.println("Fee<T> instantiated");
        }

        public Fee(T t) {
            this.t = t;
            System.out.println("Fee<T> instantiated: " + t);
        }

        public void make() {
            System.out.println(this + ": make()");
        }
    }

    private static void assertTrue(boolean cond) {
        if (!cond)
            throw new AssertionError();
    }

    public static void main(String[] args) {

        A a = Fee<String>::new; //constructor reference to Fee<T>()
        a.m().make();

        B b = Fee<String>::new; //constructor reference to Fee<T>(String)
        b.m("hi").make();

        C c = MethodRef4::new; //constructor reference to MethodRef4()
        assertTrue( c.m() instanceof MethodRef4 );
        c = MethodRef4::new; //constructor reference to MethodRef4()
        assertTrue( c.m() instanceof MethodRef4 );
        c = Fee<String>::new; //constructor reference to Fee<T>()
        assertTrue( c.m() instanceof Fee );
    }
}
