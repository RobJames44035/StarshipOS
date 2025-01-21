/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class AnonymousInSuperCallNegTest {

    static class Base {
        Base(Object o) {}
    }

    static class Outer {
        class Inner {}
    }

    public static class JavacBug extends Base {
        int x;
        JavacBug() {
            super(new Outer().new Inner() {
                void foo() {
                    System.out.println("x = " + x);
                }
            }); }
    }
}
