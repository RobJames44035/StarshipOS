/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

import org.testng.annotations.Test;

import java.util.function.Supplier;

/**
 * TestInnerCtorRef
 */
// @Test
public class TestInnerCtorRef {

    public void testCtorRef() {
        A<String> a = A.make(() -> "");
    }
}

abstract class A<T> {
    abstract T make();

    private A() {
    }

    public interface Foo<T> { }
    public static<T> A<T> make(Supplier<T> factory) {
        class Local implements Foo<T> {
            Supplier<T> f = factory;
        }
        return new Helper<T>(Local::new);
    }

    private static class Helper<T> extends A<T> {

        private Helper(Supplier<Foo<T>> factory) {
        }

        @Override
        T make() {
            return null;
        }
    }
}
