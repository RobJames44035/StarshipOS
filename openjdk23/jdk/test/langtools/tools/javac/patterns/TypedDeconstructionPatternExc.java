/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 */

import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public class TypedDeconstructionPatternExc {

    public static void main(String... args) throws Throwable {
        new TypedDeconstructionPatternExc().run();
    }

    void run() {
        run(this::testExpr);
        run(this::testExprCond);
        testTryExpr();
        run(this::testLambda);
        runBoxed();
    }

    void run(Function<Pair<String, Integer>, Integer> tested) {
        assertEquals(2, tested.apply(new Pair<>("1", 1)));
        try {
            tested.apply((Pair<String, Integer>) (Object) new Pair<Integer, Integer>(1, 1));
            fail("Expected an exception, but none happened!");
        } catch (ClassCastException ex) {
            System.err.println("expected exception:");
            ex.printStackTrace();
        }
        try {
            tested.apply(new Pair<String, Integer>("fail", 1));
            fail("Expected an exception, but none happened!");
        } catch (MatchException ex) {
            assertEquals(TestPatternFailed.class.getName() + ": " + EXCEPTION_MESSAGE,
                         ex.getMessage());
            if (ex.getCause() instanceof TestPatternFailed ex2) {
                System.err.println("expected exception:");
                ex2.printStackTrace();
            } else {
                fail("Not the correct exception.");
            }
        }
    }

    int testExpr(Pair<String, Integer> p) {
        return switch (p) {
            case Pair<String, Integer>(String s, Integer i) -> s.length() + i;
            case Object o -> -1;
        };
    }

    int testExprCond(Pair<String, Integer> p) {
        if (switch (p) {
            case Pair<String, Integer>(String s, Integer i) -> true;
            case Object o -> false;
        }) {
            return p.l().length() + p.r();
        } else {
            return -1;
        }
    }

    void testTryExpr() {
        TEST: {
            try {
                var v = switch ((Pair<String, Integer>) (Object) new Pair<Integer, Integer>(1, 1)) {
                    case Pair<String, Integer>(String s, Integer i) -> s.length() + i;
                    case Object o -> -1;
                };
            } catch (ClassCastException ex) {
                //OK
                break TEST;
            } catch (Throwable t) {
                t.printStackTrace();
                fail("Unexpected Throwable!");
            }
            fail("ClassCastException not thrown!");
        }
        TEST: {
            try {
                var v = switch (new Pair<String, Integer>("fail", 1)) {
                    case Pair<String, Integer>(String s, Integer i) -> s.length() + i;
                    case Object o -> -1;
                };
            } catch (MatchException ex) {
                //OK
                break TEST;
            } catch (Throwable t) {
                t.printStackTrace();
                fail("Unexpected Throwable!");
            }
            fail("MatchException not thrown!");
        }
    }

    int testLambda(Pair<String, Integer> p) {
        var r = prepareLambda();
        return r.applyAsInt(p);
    }

    ToIntFunction<Pair<String, Integer>> prepareLambda() {
        return p -> switch (p) {
            case Pair<String, Integer>(String s, Integer i) -> s.length() + i;
            case Object o -> -1;
        };
    }

    void runBoxed() {
        assertEquals(2, testBoxed(new Box(new Pair<>("1", 1))));
        try {
            testBoxed(new Box((Pair<String, Integer>) (Object) new Pair<Integer, Integer>(1, 1)));
            fail("Expected an exception, but none happened!");
        } catch (ClassCastException ex) {
            System.err.println("expected exception:");
            ex.printStackTrace();
        }
        try {
            testBoxed(new Box(new Pair<String, Integer>("fail", 1)));
            fail("Expected an exception, but none happened!");
        } catch (MatchException ex) {
            assertEquals(TestPatternFailed.class.getName() + ": " + EXCEPTION_MESSAGE,
                         ex.getMessage());
            if (ex.getCause() instanceof TestPatternFailed ex2) {
                System.err.println("expected exception:");
                ex2.printStackTrace();
            } else {
                fail("Not the correct exception.");
            }
        }
    }

    int testBoxed(Object p) {
        return switch (p) {
            case Box(Pair<String, Integer>(String s, Integer i)) -> s.length() + i;
            case Object o -> -1;
        };
    }

    static final String EXCEPTION_MESSAGE = "exception-message";

    record Pair<L, R>(L l, R r) {
        public L l() {
            if ("fail".equals(l)) {
                throw new TestPatternFailed(EXCEPTION_MESSAGE);
            }
            return l;
        }
        public R r() {
            return r;
        }
    }

    record Box(Pair<String, Integer> boxed) {}

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
