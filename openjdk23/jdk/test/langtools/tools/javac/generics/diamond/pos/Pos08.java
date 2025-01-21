/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8133135
 *
 * @summary Compiler internall error (NPE) on anonymous class defined by qualified instance creation expression with diamond
 * @author sadayapalam
 * @compile Pos08.java
 *
 */

class Pos08 {

    static class List<T> {
    }

    static class FooOuter {
        class Foo<T> {
            public Foo(){}
        }
    }

    public static <T> List<T> m(List<T> list, T item) {
        return list;
    }


    public static void run() {
        m(new List<FooOuter.Foo<String>>(), new FooOuter().new Foo<>(){ });
    }
}
