/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @bug 8300543
 * @summary Check switches work correctly with qualified enum constants
 * @compile EnumSwitchQualified.java
 * @run main EnumSwitchQualified
*/

import java.util.Objects;

public class EnumSwitchQualified {

    public static void main(String... args) {
        new EnumSwitchQualified().run();
    }

    void run() {
        assertEquals(1, testPatternMatchingSwitch1(E1.A));
        assertEquals(2, testPatternMatchingSwitch1(E1.B));
        assertEquals(3, testPatternMatchingSwitch1(E1.C));
        assertEquals(4, testPatternMatchingSwitch1(E2.B));
        assertEquals(5, testPatternMatchingSwitch1(E2.C));
        assertEquals(6, testPatternMatchingSwitch1(E2.D));

        assertEquals(1, testPatternMatchingSwitch2(E1.A));
        assertEquals(2, testPatternMatchingSwitch2(E1.B));
        assertEquals(3, testPatternMatchingSwitch2(E1.C));

        assertEquals(1, testPatternMatchingSwitch3(E1.A));
        assertEquals(2, testPatternMatchingSwitch3(E1.B));
        assertEquals(3, testPatternMatchingSwitch3(E1.C));
        assertEquals(4, testPatternMatchingSwitch3(E2.B));
        assertEquals(5, testPatternMatchingSwitch3(E2.C));
        assertEquals(6, testPatternMatchingSwitch3(E2.D));
        assertEquals(7, testPatternMatchingSwitch3(""));
    }

    int testPatternMatchingSwitch1(I i) {
        return switch(i) {
            case E1.A -> 1;
            case E1.B -> 2;
            case E1.C -> 3;
            case E2.B -> 4;
            case E2.C -> 5;
            case E2.D -> 6;
        };
    }

    int testPatternMatchingSwitch2(E1 e) {
        return switch(e) {
            case E1.A -> 1;
            case E1.B -> 2;
            case E1.C -> 3;
        };
    }

    int testPatternMatchingSwitch3(Object o) {
        return switch(o) {
            case E1.A -> 1;
            case E1.B -> 2;
            case E1.C -> 3;
            case E2.B -> 4;
            case E2.C -> 5;
            case E2.D -> 6;
            default -> 7;
        };
    }

    private void assertEquals(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("Incorrect result, expected: " + expected +
                                     ", actual: " + actual);
        }
    }

    sealed interface I {}
    enum E1 implements I { A, B, C; }
    enum E2 implements I { B, C, D; }

}
