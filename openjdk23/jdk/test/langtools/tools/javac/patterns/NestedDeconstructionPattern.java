/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 */

import java.util.Objects;

public class NestedDeconstructionPattern {

    public static void main(String... args) throws Throwable {
        new NestedDeconstructionPattern().doTestR();
        new NestedDeconstructionPattern().doTestP();
    }

    void doTestR() {
        assertEquals("AA", switchR1(new R(new A(), new A())));
        assertEquals("AB", switchR1(new R(new A(), new B())));
        assertEquals("BA", switchR1(new R(new B(), new A())));
        assertEquals("BB", switchR1(new R(new B(), new B())));
        try {
            switchR1(null);
            throw new AssertionError("Didn't get a NPE.");
        } catch (NullPointerException ex) {
            //OK
        }
        assertEquals("AA", switchR2(new R(new A(), new A())));
        assertEquals("AB", switchR2(new R(new A(), new B())));
        assertEquals("BA", switchR2(new R(new B(), new A())));
        assertEquals("BB", switchR2(new R(new B(), new B())));
        assertEquals("other", switchR2(""));
        try {
            switchR2(null);
            throw new AssertionError("Didn't get a NPE.");
        } catch (NullPointerException ex) {
            //OK
        }
    }

    String switchR1(R r) {
        return switch (r) {
            case R(A a, A b) -> a.name() + b.name();
            case R(A a, B b) -> a.name() + b.name();
            case R(B a, A b) -> a.name() + b.name();
            case R(B a, B b) -> a.name() + b.name();
        };
    }

    String switchR2(Object o) {
        return switch (o) {
            case R(A a, A b) -> a.name() + b.name();
            case R(A a, B b) -> a.name() + b.name();
            case R(B a, A b) -> a.name() + b.name();
            case R(B a, B b) -> a.name() + b.name();
            default -> "other";
        };
    }

    void doTestP() {
        assertEquals("AAAA", switchP1(new P(new R(new A(), new A()), new R(new A(), new A()))));
    }

    String switchP1(P p) {
        return switch (p) {
            case P(R(A a1, A b1), R(A a2, A b2)) -> a1.name() + b1.name()+a2.name() + b2.name();
            case P(R(A a1, A b1), R(A a2, B b2)) -> a1.name() + b1.name()+a2.name() + b2.name();
            case P(R(A a1, A b1), R(B a2, A b2)) -> a1.name() + b1.name()+a2.name() + b2.name();
            case P(R(A a1, A b1), R(B a2, B b2)) -> a1.name() + b1.name()+a2.name() + b2.name();
            case P(R(A a1, B b1), R(A a2, A b2)) -> a1.name() + b1.name()+a2.name() + b2.name();
            case P(R(A a1, B b1), R(A a2, B b2)) -> a1.name() + b1.name()+a2.name() + b2.name();
            case P(R(A a1, B b1), R(B a2, A b2)) -> a1.name() + b1.name()+a2.name() + b2.name();
            case P(R(A a1, B b1), R(B a2, B b2)) -> a1.name() + b1.name()+a2.name() + b2.name();
            case P(R(B a1, A b1), R(A a2, A b2)) -> a1.name() + b1.name()+a2.name() + b2.name();
            case P(R(B a1, A b1), R(A a2, B b2)) -> a1.name() + b1.name()+a2.name() + b2.name();
            case P(R(B a1, A b1), R(B a2, A b2)) -> a1.name() + b1.name()+a2.name() + b2.name();
            case P(R(B a1, A b1), R(B a2, B b2)) -> a1.name() + b1.name()+a2.name() + b2.name();
            case P(R(B a1, B b1), R(A a2, A b2)) -> a1.name() + b1.name()+a2.name() + b2.name();
            case P(R(B a1, B b1), R(A a2, B b2)) -> a1.name() + b1.name()+a2.name() + b2.name();
            case P(R(B a1, B b1), R(B a2, A b2)) -> a1.name() + b1.name()+a2.name() + b2.name();
            case P(R(B a1, B b1), R(B a2, B b2)) -> a1.name() + b1.name()+a2.name() + b2.name();
            case P(N a, N b) -> "other";
        };
    }

    public sealed interface I {}
    public final class A implements I {
        public String name() { return "A"; }
    }
    public final class B implements I {
        public String name() { return "B"; }
    }

    public record R(I a, I b) implements N {}

    public sealed interface N {}
    public final class C implements N {
        public String name() { return "B"; }
    }

    public record P(N a, N b) {
    }

    private void assertEquals(String expected, String actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + actual);
        }
    }
}
