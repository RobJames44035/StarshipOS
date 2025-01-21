/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug     8273914
 * @summary Indy string concat changes order of operations
 *
 * @clean *
 * @compile -XDstringConcat=indy              StringAppendEvaluatesInOrder.java
 * @run main StringAppendEvaluatesInOrder
 *
 * @clean *
 * @compile -XDstringConcat=indyWithConstants StringAppendEvaluatesInOrder.java
 * @run main StringAppendEvaluatesInOrder
 *
 * @clean *
 * @compile -XDstringConcat=inline            StringAppendEvaluatesInOrder.java
 * @run main StringAppendEvaluatesInOrder
 */

public class StringAppendEvaluatesInOrder {
    static String test() {
        StringBuilder builder = new StringBuilder("foo");
        int i = 15;
        return "Test: " + i + " " + (++i) + builder + builder.append("bar");
    }

    static String compoundAssignment() {
        StringBuilder builder2 = new StringBuilder("foo");
        Object oo = builder2;
        oo += "" + builder2.append("bar");
        return oo.toString();
    }

    public static void main(String[] args) throws Exception {
        assertEquals(test(), "Test: 15 16foofoobar");
        assertEquals(compoundAssignment(), "foofoobar");
    }

    private static void assertEquals(String actual, String expected) {
      if (!actual.equals(expected)) {
        throw new AssertionError("expected: " + expected + ", actual: " + actual);
      }
    }
}
