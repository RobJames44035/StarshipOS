/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @bug 8300543 8309336 8311825
 * @summary Check switches work correctly with qualified enum constants
 * @compile/fail/ref=EnumSwitchQualifiedErrors.out -XDrawDiagnostics EnumSwitchQualifiedErrors.java
*/

public class EnumSwitchQualifiedErrors {

    int testPatternMatchingSwitch1(I i) {
        return switch(i) {
            case E1.A -> 1;
            case E2.A -> 2;
        };
    }

    int testPatternMatchingSwitch2(E1 e) {
        return switch(e) {
            case E1.A -> 1;
            case E2.A -> 4;
        };
    }

    int testPatternMatchingSwitch3(Number n) {
        return switch(n) {
            case E1.A -> 1;
            case E2.A -> 2;
        };
    }

    int testPatternMatchingSwitch4(E1 e) {
        return switch(e) {
            case E1A -> 1;
            case (E1) null -> 1;
            case E1 -> 1;
            default -> {}
        };
    }

    int testPatternMatchingSwitch5(Object e) {
        return switch(e) {
            case E1A -> 1;
            case (E1) null -> 1;
            case E1 -> 1;
            default -> {}
        };
    }

    int testQualifiedDuplicate1(Object o) {
        return switch(o) {
            case E1.A -> 1;
            case E1.A -> 2;
            default -> -1;
        };
    }

    int testQualifiedDuplicate2(E1 e) {
        return switch(e) {
            case A -> 1;
            case E1.A -> 2;
            default -> -1;
        };
    }

    int testQualifiedDuplicate3(E1 e) {
        return switch(e) {
            case E1.A -> 1;
            case A -> 2;
            default -> -1;
        };
    }

    sealed interface I {}
    enum E1 implements I { A; }
    enum E2 { A; }

    static final E1 E1A = null;
}
