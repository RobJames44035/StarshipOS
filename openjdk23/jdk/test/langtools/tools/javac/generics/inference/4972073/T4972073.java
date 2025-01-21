/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class T4972073 {

    static class D {}

    static interface MyInterface<E> {
        public String foo();
    }

    static class MyClass {}

    static class Sun1 extends MyClass implements MyInterface<Sun1>, MyInterface<Sun1> {
        public String foo() {
            return "test";
        }
    }

    static class B<E extends MyClass & MyInterface<E> & MyInterface<E>> extends D {}
}
