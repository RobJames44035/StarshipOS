/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 * @bug 8298184
 * @compile GenericRecordDeconstructionPattern.java
 * @run main GenericRecordDeconstructionPattern
 */
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class GenericRecordDeconstructionPattern {

    public static void main(String... args) throws Throwable {
        new GenericRecordDeconstructionPattern().run();
    }

    void run() {
        runTest(this::runIf);
        runTest(this::runSwitch);
        runTest(this::runSwitchExpression);
        runTest(this::runSwitchInference1);
        runTest(this::runSwitchInference2);
        runTest(this::runSwitchInference3);
        runTest(this::runSwitchInference4);
        testInference3();
        assertEquals(1, runIfSuperBound(new Box<>(new StringBuilder())));
        assertEquals(1, runIfSuperBound(new Box<>(0)));
    }

    void runTest(Function<Box<String>, Integer> test) {
        Box<String> b = new Box<>(null);
        assertEquals(1, test.apply(b));
    }

    int runIf(Box<String> b) {
        if (b instanceof Box<String>(String s)) return 1;
        return -1;
    }

    int runSwitch(Box<String> b) {
        switch (b) {
            case Box<String>(String s): return 1;
            default: return -1;
        }
    }

    int runSwitchExpression(Box<String> b) {
        return switch (b) {
            case Box<String>(String s) -> 1;
            default -> -1;
        };
    }

    int runSwitchInference1(I<String> b) {
        switch (b) {
            case Box(String s): return s == null ? 1 : s.length();
            default: return -1;
        }
    }

    int runSwitchInference2(I<String> b) {
        switch (b) {
            case Box(var s): return s == null ? 1 : s.length();
            default: return -1;
        }
    }

    int runSwitchInference3(I<String> b) {
        return b instanceof Box(var s) ? s == null ? 1 : s.length()
                                       : -1;
    }

    <Z extends I<String>> int runSwitchInference4(Z b) {
        return b instanceof Box(var s) ? s == null ? 1 : s.length()
                                       : -1;
    }

    <B extends CharSequence & Runnable, Z extends I<B>> int runSwitchInference5(Z b) {
        return b instanceof Box(var s) ? s == null ? 1 : s.length()
                                       : -1;
    }

    void testInference3() {
        I<I<String>> b = new Box<>(new Box<>(null));
        assertEquals(1, runSwitchInferenceNested(b));
    }

    int runSwitchInferenceNested(I<I<String>> b) {
        switch (b) {
            case Box(Box(var s)): return s == null ? 1 : s.length();
            default: return -1;
        }
    }

    int runIfSuperBound(I<? super String> b) {
        if (b instanceof Box(var v)) return 1;
        return -1;
    }

    sealed interface I<T> {}
    record Box<V>(V v) implements I<V> {
    }

    void assertEquals(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("Expected: " + expected + "," +
                                     "got: " + actual);
        }
    }

    void fail(String message) {
        throw new AssertionError(message);
    }

    public static class TestPatternFailed extends AssertionError {

        public TestPatternFailed(String message) {
            super(message);
        }

    }

}
