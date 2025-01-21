/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8214031
 * @summary Verify various corner cases with nested switch expressions.
 * @compile ExpressionSwitchBugsInGen.java
 * @run main ExpressionSwitchBugsInGen
 */

public class ExpressionSwitchBugsInGen {
    public static void main(String... args) {
        new ExpressionSwitchBugsInGen().test(0, 0, 0, false);
        new ExpressionSwitchBugsInGen().test(0, 0, 1, true);
        new ExpressionSwitchBugsInGen().test(0, 1, 1, false);
        new ExpressionSwitchBugsInGen().test(1, 1, -1, true);
        new ExpressionSwitchBugsInGen().test(1, 12, -1, false);
        new ExpressionSwitchBugsInGen().testCommonSuperType(0, "a", new StringBuilder(), "a");
        new ExpressionSwitchBugsInGen().testCommonSuperType(1, "", new StringBuilder("a"), "a");
        new ExpressionSwitchBugsInGen().testSwitchExpressionInConditional(0, null, -1);
        new ExpressionSwitchBugsInGen().testSwitchExpressionInConditional(1, "", 0);
        new ExpressionSwitchBugsInGen().testSwitchExpressionInConditional(1, 1, 1);
        new ExpressionSwitchBugsInGen().testIntBoxing(0, 10, 10);
        new ExpressionSwitchBugsInGen().testIntBoxing(1, 10, -1);
    }

    private void test(int a, int b, int c, boolean expected) {
        if ( !(switch (a) {
                case 0 -> b == (switch (c) { case 0 -> 0; default -> 1; });
                default -> b == 12;
            })) {
            if (!expected) {
                throw new IllegalStateException();
            }
        } else {
            if (expected) {
                throw new IllegalStateException();
            }
        }
    }

    private void testCommonSuperType(int a, String s1, StringBuilder s2, String expected) {
        String r = (switch (a) {
            case 0 -> s1;
            default -> s2;
        }).toString();
        if (!expected.equals(r)) {
            throw new IllegalStateException();
        }
    }

    private void testSwitchExpressionInConditional(int a, Object o, int expected) {
        int v = a == 0 ? -1
                       : switch (o instanceof String ? 0 : 1) {
                             case 0 -> 0;
                             default -> 1;
                       };
        if (v != expected) {
            throw new IllegalStateException();
        }
    }

    private void testIntBoxing(int a, Integer res, int expected) {
        int r = switch (a) {
            case 0 -> res;
            default -> -1;
        };
        if (r != expected) {
            throw new IllegalStateException();
        }
    }

}
