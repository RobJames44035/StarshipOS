/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * bug 8148354
 * @summary Errors targeting functional interface intersection types
 * @compile IntersectionTypeBugTest.java
 */

import java.io.Serializable;
import java.util.function.Consumer;

class IntersectionTypeBugTest {
    <T extends Object & Serializable & Consumer<String>> void consume(final T cons, final String s) {}

    void process(final String s) {}

    public void foo() {
        consume(this::process, "Hello World");
    }

    // another case
    static class AnotherTest<T> {
        void foo() {
            Object r = (Object & Serializable & R<T>) () -> {};
        }

        interface R<I> {
            void foo();
        }
    }
}
