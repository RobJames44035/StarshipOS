/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8262891
 * @summary Verify pattern switches work properly when the set of enum constant changes.
 * @compile EnumTypeChanges.java
 * @compile EnumTypeChanges2.java
 * @run main EnumTypeChanges
 */

import java.util.function.Function;
import java.util.Objects;

public class EnumTypeChanges {

    public static void main(String... args) throws Exception {
        new EnumTypeChanges().run();
    }

    void run() throws Exception {
        doRun(this::statementEnum);
        doRun(this::expressionEnum);
        doRunExhaustive(this::expressionEnumExhaustive);
        doRunExhaustive(this::statementEnumExhaustive);
    }

    void doRun(Function<EnumTypeChangesEnum, String> c) throws Exception {
        assertEquals("A", c.apply(EnumTypeChangesEnum.A));
        assertEquals("D", c.apply(EnumTypeChangesEnum.valueOf("C")));
    }

    void doRunExhaustive(Function<EnumTypeChangesEnum, String> c) throws Exception {
        try {
            c.apply(EnumTypeChangesEnum.valueOf("C"));
            throw new AssertionError();
        } catch (MatchException e) {
            //expected
        }
    }

    String statementEnum(EnumTypeChangesEnum e) {
        switch (e) {
            case A -> { return "A"; }
            case B -> { return "B"; }
            case EnumTypeChangesEnum e1 when e1 == null -> throw new AssertionError();
            default -> { return "D"; }
        }
    }

    String expressionEnum(EnumTypeChangesEnum e) {
        return switch (e) {
            case A -> "A";
            case B -> "B";
            case EnumTypeChangesEnum e1 when e1 == null -> throw new AssertionError();
            default -> "D";
        };
    }

    String statementEnumExhaustive(EnumTypeChangesEnum e) {
        switch (e) {
            case A -> { return "A"; }
            case B -> { return "B"; }
            case EnumTypeChangesEnum x when e == EnumTypeChangesEnum.A -> throw new AssertionError();
        }
    }

    String expressionEnumExhaustive(EnumTypeChangesEnum e) {
        return switch (e) {
            case A -> "A";
            case B -> "B";
            case EnumTypeChangesEnum x when e == EnumTypeChangesEnum.A -> throw new AssertionError();
        };
    }

    private static void assertEquals(Object o1, Object o2) {
        if (!Objects.equals(o1, o2)) {
            throw new AssertionError();
        }
    }
}

enum EnumTypeChangesEnum {
    A,
    B;
}
