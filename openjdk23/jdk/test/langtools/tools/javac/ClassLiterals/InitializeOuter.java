/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4401576
 * @summary Using a class literal causes outermost class to be initialized early
 * @author gafter
 *
 * @compile InitializeOuter.java
 * @run main InitializeOuter
 */

public class InitializeOuter {
    public static void main(String[] args) {
        new A.B();
    }
}

class A {
    static {
        if (true) throw new Error();
    }
    public static class B {
        B() {
            Object o = InitializeOuter.class;
        }
    };
}
