/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8034048
 * @summary javac crash with method references plus lambda plus var args
 * @author govereau
 *
 * @compile  MethodHandleCrash.java
 */
public interface MethodHandleCrash {
    static<T> void functional(T... input) {
        java.util.function.Consumer<T> c = MethodHandleCrash::functional;
    }
}
