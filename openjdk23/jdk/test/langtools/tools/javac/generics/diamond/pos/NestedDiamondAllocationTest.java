/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8081521
 * @summary Ensure that anonymous class construction using <> can be nested within another
 * @compile NestedDiamondAllocationTest.java
 * @run main NestedDiamondAllocationTest
 *
 */

public class NestedDiamondAllocationTest {
    static class Clazz2 {
        static class A {
        };
        public A a;
    }
    static class FooNest<Q> {
        FooNest(Q q, Foo<Q> foo) {
        }
    }

    static class Foo<T> {
    }

    static Clazz2 clazz = new Clazz2();

    public static void main(String [] args) {
        FooNest fooNest = new FooNest<>(clazz.a, new Foo<>() {
        });
    }
}
