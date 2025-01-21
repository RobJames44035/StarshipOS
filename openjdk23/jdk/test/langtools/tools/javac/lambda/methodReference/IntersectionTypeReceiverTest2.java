/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @bug 8142476
 * @summary Test that Call site initialization exception is not thrown when the method
   reference receiver is of intersection type.
 * @run main IntersectionTypeReceiverTest2
 */

public class IntersectionTypeReceiverTest2 {
    interface I {
    }

    interface J {
        void foo();
    }

    static <T extends I & J> void bar(T t) {
        Runnable r = t::foo;
    }

    public static void main(String[] args) {
        class A implements I, J {
            public void foo() {
            }
        }
        bar(new A());
    }
}
