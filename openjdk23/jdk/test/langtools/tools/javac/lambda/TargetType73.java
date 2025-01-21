/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8014494
 * @summary javac crashes when varargs element of a method reference is inferred from the context
 * @compile TargetType73.java
 */
import java.util.List;

class TargetType73 {

    interface Function<X,Y> {
        Y m(X x);
    }

    static void test() {
        m(TargetType73::g);
    }

    public static <T> List<T> g(T... a) {
        return null;
    }

    public static <C> void m(Function<String, C> zipper) {  }
}
