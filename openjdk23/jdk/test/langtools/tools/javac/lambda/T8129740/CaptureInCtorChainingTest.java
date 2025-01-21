/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8129740 8133111
 * @summary Incorrect class file created when passing lambda in inner class constructor
 * @run main CaptureInCtorChainingTest
 */

import java.util.function.Consumer;
import java.util.function.Function;

public class CaptureInCtorChainingTest {

    CaptureInCtorChainingTest(Function<Function<Function<Consumer<Void>, Void>, Void>, Void> innerClass) {
        new InnerClass(innerClass);
    }

    void foo(Void v) { }

    class InnerClass {

        InnerClass(Function<Function<Function<Consumer<Void>, Void>, Void>, Void> factory) {
            this(factory.apply(o -> o.apply(CaptureInCtorChainingTest.this::foo)));
        }

        InnerClass(Void unused) { }
    }

    public static void main(String[] args) {
        new CaptureInCtorChainingTest(o -> null);
    }
}
