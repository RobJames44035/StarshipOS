/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8318144
 * @summary Verify switches work in presence of enum constants that have bodies
 * @compile SwitchEnumConstants.java
 * @run main SwitchEnumConstants
 */

import java.util.function.ToIntFunction;

public class SwitchEnumConstants {

    public static void main(String... args) throws Exception {
        new SwitchEnumConstants().run();
    }

    void run() throws Exception {
        doRun(this::typeSwitch);
        doRun(this::enumSwitch);
    }

    void doRun(ToIntFunction<Object> c) throws Exception {
        assertEquals(0, c.applyAsInt(E.A));
        assertEquals(1, c.applyAsInt(E.B));
        assertEquals(2, c.applyAsInt(E.C));
        assertEquals(3, c.applyAsInt(""));
    }

    int typeSwitch(Object o) {
        return switch (o) {
            case E.A -> 0;
            case E.B -> 1;
            case E.C -> 2;
            case String s -> 3;
            default -> throw new IllegalStateException();
        };
    }

    int enumSwitch(Object o) {
        if (!(o instanceof E e)) {
            return 3;
        }
        return switch (e) {
            case A -> 0;
            case B -> 1;
            case C -> 2;
        };
    }


    private static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("expected: " + expected +
                                     ", actual: " + actual);
        }
    }

    enum E {
        A {},
        B {},
        C {}
    }
}
